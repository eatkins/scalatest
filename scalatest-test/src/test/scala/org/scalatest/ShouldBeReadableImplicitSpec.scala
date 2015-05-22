/*
 * Copyright 2001-2013 Artima, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.scalatest

import SharedHelpers.{createTempDirectory, thisLineNumber}
import enablers.Readability
import Matchers._
import exceptions.TestFailedException

class ShouldBeReadableImplicitSpec extends FunSpec {
  
  trait Thing {
    def canRead: Boolean
  }
  
  val book = new Thing {
    val canRead = true
  }
  
  val stone = new Thing {
    val canRead = false
  }
  
  implicit def readabilityOfThing[T <: Thing]: Readability[T] =
    new Readability[T] {
      def isReadable(thing: T): Boolean = thing.canRead
    }
  
  val fileName: String = "ShouldBeReadableImplicitSpec.scala"
    
  def wasNotReadable(left: Any): String = 
    FailureMessages.wasNotReadable(left)
    
  def wasReadable(left: Any): String = 
    FailureMessages.wasReadable(left)
  
  it("book should be readable, stone should not be readable") {
    assert(book.canRead === true)
    assert(stone.canRead === false)
  }
  
  def allError(left: Any, message: String, lineNumber: Int): String = {
    val messageWithIndex = UnquotedString("  " + FailureMessages.forAssertionsGenTraversableMessageWithStackDepth(0, UnquotedString(message), UnquotedString(fileName + ":" + lineNumber)))
    FailureMessages.allShorthandFailed(messageWithIndex, left)
  }
  
  describe("Readable matcher") {
    
    describe("when work with 'file should be (readable)'") {
      
      it("should do nothing when file is readable") {
        book should be (readable)
      }
      
      it("should throw TestFailedException with correct stack depth when file is not readable") {
        val caught1 = intercept[TestFailedException] {
          stone should be (readable)
        }
        assert(caught1.message === Some(wasNotReadable(stone)))
        assert(caught1.failedCodeFileName === Some(fileName))
        assert(caught1.failedCodeLineNumber === Some(thisLineNumber - 4))
      }
      
    }
    
    describe("when work with 'file should not be readable'") {
      
      it("should do nothing when file is not readable") {
        stone should not be readable
      }
      
      it("should throw TestFailedException with correct stack depth when file is readable") {
        val caught1 = intercept[TestFailedException] {
          book should not be readable
        }
        assert(caught1.message === Some(wasReadable(book)))
        assert(caught1.failedCodeFileName === Some(fileName))
        assert(caught1.failedCodeLineNumber === Some(thisLineNumber - 4))
      }
    }
    
    describe("when work with 'file shouldBe readable'") {
      
      it("should do nothing when file is readable") {
        book shouldBe readable
      }
      
      it("should throw TestFailedException with correct stack depth when file is not readable") {
        val caught1 = intercept[TestFailedException] {
          stone shouldBe readable
        }
        assert(caught1.message === Some(wasNotReadable(stone)))
        assert(caught1.failedCodeFileName === Some(fileName))
        assert(caught1.failedCodeLineNumber === Some(thisLineNumber - 4))
      }
      
    }
    
    describe("when work with 'file shouldNot be (readable)'") {
      
      it("should do nothing when file is not readable") {
        stone shouldNot be (readable)
      }
      
      it("should throw TestFailedException with correct stack depth when file is readable") {
        val caught1 = intercept[TestFailedException] {
          book shouldNot be (readable)
        }
        assert(caught1.message === Some(wasReadable(book)))
        assert(caught1.failedCodeFileName === Some(fileName))
        assert(caught1.failedCodeLineNumber === Some(thisLineNumber - 4))
      }
      
    }
    
    describe("when work with 'all(xs) should be (readable)'") {
      
      it("should do nothing when all(xs) is readable") {
        all(List(book)) should be (readable)
      }
      
      it("should throw TestFailedException with correct stack depth when all(xs) is not readable") {
        val left1 = List(stone)
        val caught1 = intercept[TestFailedException] {
          all(left1) should be (readable)
        }
        assert(caught1.message === Some(allError(left1, wasNotReadable(stone), thisLineNumber - 2)))
        assert(caught1.failedCodeFileName === Some(fileName))
        assert(caught1.failedCodeLineNumber === Some(thisLineNumber - 4))
      }
      
    }
    
    describe("when work with 'all(xs) should not be readable'") {
      
      it("should do nothing when all(xs) is not readable") {
        all(List(stone)) should not be readable
      }
      
      it("should throw TestFailedException with correct stack depth when all(xs) is readable") {
        val left1 = List(book)
        val caught1 = intercept[TestFailedException] {
          all(left1) should not be readable
        }
        assert(caught1.message === Some(allError(left1, wasReadable(book), thisLineNumber - 2)))
        assert(caught1.failedCodeFileName === Some(fileName))
        assert(caught1.failedCodeLineNumber === Some(thisLineNumber - 4))
      }
      
    }
    
    describe("when work with 'all(xs) shouldBe readable'") {
      
      it("should do nothing when all(xs) is readable") {
        all(List(book)) shouldBe readable
      }
      
      it("should throw TestFailedException with correct stack depth when all(xs) is not readable") {
        val left1 = List(stone)
        val caught1 = intercept[TestFailedException] {
          all(left1) shouldBe readable
        }
        assert(caught1.message === Some(allError(left1, wasNotReadable(stone), thisLineNumber - 2)))
        assert(caught1.failedCodeFileName === Some(fileName))
        assert(caught1.failedCodeLineNumber === Some(thisLineNumber - 4))
      }
    }
    
    describe("when work with 'all(xs) shouldNot be (readable)'") {
      
      it("should do nothing when all(xs) is not readable") {
        all(List(stone)) shouldNot be (readable)
      }
      
      it("should throw TestFailedException with correct stack depth when all(xs) is readable") {
        val left1 = List(book)
        val caught1 = intercept[TestFailedException] {
          all(left1) shouldNot be (readable)
        }
        assert(caught1.message === Some(allError(left1, wasReadable(book), thisLineNumber - 2)))
        assert(caught1.failedCodeFileName === Some(fileName))
        assert(caught1.failedCodeLineNumber === Some(thisLineNumber - 4))
      }
      
    }
  }
  
}
