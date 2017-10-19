package com.query.main

/**
 * Created by admin on 2016/11/10.
 *
 * GeoHash base on Spark whith
 */

import com.openStreetMap._
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel._
import com.index.TrieTree

object QueryInMemory{

  def main(args: Array[String]) {

    //Initialize the spark context.
    val conf = new SparkConf()
      .setAppName("Query In Memory toArray")
//      .set("spark.kryoserializer.buffer.max","1024")
//      .setMaster("spark://master:7077")
//      .setJars(List("C:\\Users\\admin\\IdeaProjects\\SpatialQueryInMemory - toArray\\out\\artifacts\\SpatialQueryInMemory_toArray_jar\\SpatialQueryInMemory.jar"))
    val sc = new SparkContext(conf)

    val fileName = if(args.length>0) args(0) else "2m"
    val fileAddress = "hdfs://master:9000/yc_input/ThreeSystem/osm_4_"+fileName+".txt"

    val Precision = if(args.length>1) args(1).toInt else 7

    val rdd = sc.textFile(fileAddress,8).map(_.split("\t"))//Beijing_density_5000.csv

//  val rdd3 = rdd.filter(_.length==4).map(x=>(x(0).substring(0,Precision),x(3)+","+x(1)+","+x(2))).groupByKey()//.persist(MEMORY_AND_DISK)
    val rdd3 = rdd.filter(_.length==4).map(x=>(x(0).substring(0,Precision),x(3))).groupByKey()//.persist(MEMORY_AND_DISK)
    val rdd3_Array = rdd3.collect()


    //rdd4_1: (Long, (String,Iterable()) )
    val trieTree = new TrieTree()
    for(i <- rdd3_Array.indices)
    {
      trieTree.insert(rdd3_Array(i)._1,i)
    }

    //Query Part
    while(true) {

      println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
      println()
      println("Input Query GeoHash code")
      println()

      //输入查询条件
      val coordinate = Console.readLine().split(" ")
      val lon = coordinate(0).toDouble
      val lat = coordinate(1).toDouble
      val range = coordinate(2).toInt
      val Geo_input = GeoHashCompute.encode(lon,lat).substring(0,Precision)


      //start
      val start = System.currentTimeMillis()
      val nanoStart = System.nanoTime()

      val qc = new QueryCircle()
      val circleNum = qc.getCircleNum(Precision, range)
      var totalMVR = 4*(circleNum*circleNum+circleNum)+1

      val start2 = System.currentTimeMillis()
      val nanoStart2 = System.nanoTime()


      println("[INFO][GeoHash]\t" + "GeoInput:"+ Geo_input +"\t" + "totalMBR:"+totalMVR +"\n")

      while(totalMVR>0) //to find all the points in every Cell (Geohash). and put p oint into "resultPoints"
      {
        val index = trieTree.getIndex(Geo_input).toInt
        if(index>=0)
        {
          var numPoints = rdd3_Array(index)._2.size //CompactBuffer.size  means Numbers of POI in the rowkey
          while(numPoints>0)
          {
              if( DistanceOfCoord.GetDistance(lon,lat,lon,lat)<range ){
                //In the circle.  Print this Point.
              }
              numPoints=numPoints-1
          }
        }
        totalMVR = totalMVR-1
      }

      val index = trieTree.getIndex(Geo_input).toInt
      if(index>=0)
        {
          println("[INFO][Query Complete]\t"+Geo_input+"->" + rdd3_Array(index)._2)
        }

      val end = System.currentTimeMillis()
      val nanoEnd = System.nanoTime()
      //End

      println("++++++++++++++++++++++++++++++++++")
      println()
      println("Query Time          :"+ (end-start)+"\t"+(nanoEnd-nanoStart) +"\t"+(nanoEnd-nanoStart2))
      println("Query And Print Time:"+ (System.currentTimeMillis()-start)+"\t"+(System.nanoTime()-nanoStart)+"\t"+(System.nanoTime()-nanoStart2))
      println()
      println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")

    }

    sc.stop()

  }

}
