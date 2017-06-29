package org.apache.spot.utilities.data.validation

import org.apache.spark.sql.types._
import org.scalatest.{FlatSpec, Matchers}

class InputSchemaTest extends FlatSpec with Matchers {

  "validate" should "return a Seq[String] of size 1 when incoming schema is valid" in {

    val incomingSchema = StructType(List(StructField("ip", StringType),
      StructField("ibyt", LongType),
      StructField("host", StringType),
      StructField("score", FloatType)))

    val modelSchema = StructType(List(StructField("ip", StringType),
      StructField("ibyt", LongType),
      StructField("host", StringType)))

    val results = InputSchema.validate(incomingSchema, modelSchema)

    results.length shouldBe 1
  }

  it should "return a Seq[String] of size > 1 when incoming schema is not valid due to type mismatch" in {
    val incomingSchema = StructType(List(StructField("ip", StringType),
      StructField("ibyt", StringType),
      StructField("host", IntegerType),
      StructField("score", FloatType)))

    val modelSchema = StructType(List(StructField("ip", StringType),
      StructField("ibyt", LongType),
      StructField("host", StringType)))

    val results = InputSchema.validate(incomingSchema, modelSchema)

    results.length shouldBe 3
  }

  it should "return a Seq[String] of size > 1 when incoming schema is not valid due to required field is missing" in {

    val incomingSchema = StructType(List(StructField("ip", StringType),
      StructField("ibyt", LongType),
      StructField("score", FloatType)))

    val modelSchema = StructType(List(StructField("ip", StringType),
      StructField("ibyt", LongType),
      StructField("host", StringType)))

    val results = InputSchema.validate(incomingSchema, modelSchema)

    results.length shouldBe 2
  }
}
