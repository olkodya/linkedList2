import scalafx.application.JFXApp3
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, RadioButton, TextField, ToggleGroup}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.Black
import scalafx.scene.text.Text
import scalafx.stage

object Main extends JFXApp3 {
  private var useFraction = false

  override def start(): Unit = {
    var listInt: MyList = new MyList
    var listFrac: MyList = new MyList
    listInt = fillList(false)
    listFrac = fillList(true)
    stage = new JFXApp3.PrimaryStage {
      title = "Linked List"
      scene = new Scene {
        fill = Color.rgb(252, 223, 255)
        content = new VBox {
          fillWidth = true
          alignment = Pos.Center
          padding = Insets(20)
          spacing = 10

          val dataTypeToggleGroup = new ToggleGroup()


          val integerRadioButton: RadioButton = new RadioButton("Integer") {
            toggleGroup = dataTypeToggleGroup
            selected = true
            onAction = _ => {
              useFraction = false
              outputText.text = listInt.toString
              //listInt = fillList()
              outputText2.text = ""

            }
            textFill = Black
          }

          val fractionRadioButton: RadioButton = new RadioButton("Fraction") {
            toggleGroup = dataTypeToggleGroup
            onAction = _ => {
              useFraction = true
              outputText.text = listFrac.toString
              outputText2.text = ""
             // listFrac = fillList()
            }
            textFill = Black
          }

          val outputLabel: Label = new Label("List:") {
            textFill = Black
          }

          val outputText: Text = new Text() {
            fill = Black
            if (useFraction) {
             text = listFrac.toString
            } else {
              text = listInt.toString
            }
          }

          val outputText2: Text = new Text() {
            fill = Black
            text = " "
          }

          val valueField = new TextField()
          valueField.promptText = "Enter value"

          val buttons: HBox = new HBox {
            fillHeight = true
            spacing = 10
            children = Seq(
              new Button("Push") {
                onAction = _ => {
                  try {
                    val values = valueField.text.value.split(" ")
                    if (useFraction) {
                      if (values.length == 2) {
                         val integerPart = values(0).toInt
                        val frPart = values(1).split("/")
                        if(frPart.length == 2){
                          val numerator = frPart(0).toInt
                          val denominator = frPart(1).toInt
                          var fraction = new Fraction(integerPart, numerator, denominator)
                          listFrac.add(fraction)
                          outputText2.text = "Pushed: " + listFrac.get(listFrac.getSize - 1).getData
                          outputText.text = listFrac.toString
                        }else {
                          outputText2.text = "Invalid input format. Please enter integerPart numerator/denominator separated by spaces."

                        }
                      } else {
                        outputText2.text = "Invalid input format. Please enter integerPart numerator/denominator separated by spaces."
                      }
                    } else {
                      if (values.length == 1) {
                        val intValue = values(0).toInt
                        listInt.add(new MyInteger(intValue))
                        outputText2.text = "Pushed: " + listInt.get(listInt.getSize - 1).getData
                        outputText.text = listInt.toString
                      } else {
                        outputText2.text = "Invalid input format. Please enter a single integer value."
                      }
                    }
                  } catch {
                    case e: NumberFormatException => outputText2.text = "Invalid input"
                    case e: IllegalArgumentException => outputText2.text = "Invalid fraction"
                  }

                }
              },
              new Button("Pop") {
                onAction = _ => {
                  try {
                    val index = valueField.text.value.toInt
                    if(useFraction){
                      val popped = listFrac.remove(index)
                      outputText2.text = "Popped: " + popped
                      outputText.text = listFrac.toString
                    }else{
                      val popped = listInt.remove(index)
                      outputText2.text = "Popped: " + popped
                      outputText.text = listInt.toString
                    }
                  } catch {
                    case e: NullPointerException => outputText2.text = "List is empty"
                    case e: NumberFormatException => outputText2.text = "Invalid input"
                    case e: IllegalArgumentException => outputText2.text = "Invalid index"
                  }
                }
              },
              new Button("Insert") {
                onAction = _ => {
                  try {
                    val input = valueField.text.value.split(" ")
                    val index = input(0).toInt
                    if (useFraction) {
                      if (input.length == 3) {
                        try {
                          val integerPart = input(1).toInt
                          val frPart = input(2).split("/")
                          if(frPart.length == 2){
                            val numerator = frPart(0).toInt
                            val denominator = frPart(1).toInt
                            val fraction = new Fraction(integerPart, numerator, denominator)
                            listFrac.add(fraction, index)
                            outputText2.text = s"Inserted at index $index: ${listFrac.get(index).getData}"
                            outputText.text = listFrac.toString
                          }else {
                            outputText2.text = "Invalid input format. Please enter index integerPart numerator/denominator separated by spaces."
                          }
                        } catch {
                          case e: IndexOutOfBoundsException => outputText2.text = s"Index out of bounds: $index"
                          case e: IllegalArgumentException => outputText2.text = "Invalid input"
                        }
                      } else {
                        outputText2.text = "Invalid input format. Please enter index, integerPart, numerator, denominator separated by spaces."
                      }
                    } else {
                      if (input.length == 2) {
                        try {
                          val intValue = input(1).toInt
                          listInt.add(new MyInteger(intValue), index)
                          outputText2.text = s"Inserted at index $index: ${listInt.get(index).getData}"
                          outputText.text = listInt.toString
                        } catch {
                          case e: IndexOutOfBoundsException => outputText2.text = s"Index out of bounds: $index"
                        }
                      } else {
                        outputText2.text = "Invalid input format. Please enter index and a single integer value."
                      }
                    }
                  } catch {
                    case e: NumberFormatException => outputText2.text = "Invalid input"
                    case e: IllegalArgumentException => outputText2.text = "Invalid index"
                  }
                }
              },
              new Button("Sort") {
                onAction = _ => {
                  try {
                    if(useFraction){
                      listFrac.quickSort(0, listFrac.getSize - 1, new Fraction().getTypeComparator)
                      outputText.text = listFrac.toString
                    }else{
                      listInt.quickSort(0, listInt.getSize - 1, new MyInteger().getTypeComparator)
                      outputText.text = listInt.toString
                    }
                    outputText2.text = "Sorted!"
                  }
                  catch {
                    case e: NullPointerException => outputText2.text = "List is empty"
                  }
                }
              },
              new Button("Get by index") {
                onAction = _ => {
                  try {
                    val index = valueField.text.value.toInt
                    if(useFraction){
                      val element = listFrac.get(index)
                      outputText2.text = s"Element at index $index: ${element.getData}"
                    }else {
                      val element = listInt.get(index)
                      outputText2.text = s"Element at index $index: ${element.getData}"
                    }
                  } catch {
                    case e: IndexOutOfBoundsException => outputText2.text = "Index out of bounds"
                    case e: NullPointerException => outputText2.text = "List is empty"
                    case e: NumberFormatException => outputText2.text = "Invalid input"
                    case e: IllegalArgumentException => outputText2.text = "Invalid index"
                  }
                }
              }
              ,

              new Button("Export") {
                onAction = _ => {
                  try {
                    if (useFraction) {
                      MyList.serializeToBinary(listFrac, "frac.out")
                    } else {
                      MyList.serializeToBinary(listInt, "int.out")
                    }
                    outputText2.text = "Export successful"
                  } catch {
                    case e: Exception =>
                      outputText2.text = "Export failed"
                  }
                }
              },
              new Button("Import") {
                onAction = _ => {
                  try {
                    if (useFraction) {
                      listFrac = MyList.deserializeFromBinary("frac.out")
                      outputText.text = listFrac.toString
                    } else {
                      listInt = MyList.deserializeFromBinary("int.out")
                      outputText.text = listInt.toString
                    }
                    outputText2.text = "Import successful"
                  } catch {
                    case e: Exception =>
                      outputText2.text = "Import failed"
                  }
                }
              },
              new Button("Clear") {
                onAction = _ => {
                  try {
                    if (useFraction) {
                      listFrac.clear()
                      outputText.text = listFrac.toString
                    } else {
                      listInt.clear()
                      outputText.text = listInt.toString
                    }
                    outputText2.text = "Clear successful"
                  } catch {
                    case e: Exception =>
                      outputText2.text = "Import failed"
                  }
                }
              },
            new Button("Fill with 10 random values") {
              onAction = _ => {
                try {
                  if (useFraction) {
                    listFrac = fillList(true)
                    outputText.text = listFrac.toString

                  } else {
                    listInt = fillList(false)
                    outputText.text = listInt.toString

                  }
                  outputText2.text = "Successful"
                } catch {
                  case e: Exception =>
                    outputText2.text = "Failed"
                }
              }
            }

            )
          }



          children = Seq(
            new HBox {
              spacing = 10
              children = Seq(
                integerRadioButton,
                fractionRadioButton
              )
            },
            outputLabel,
            outputText,
            outputText2,
            valueField,
            buttons
          )
        }
      }
    }

    stage.resizable = true
  }

  private def fillList(boolean: Boolean): MyList = {
    var list = new MyList
    for (i <- 0 until 10) {
      if (boolean) {
        var fraction = Fraction.create
        list.add(fraction)
      }else {
        var integer = MyInteger.create
        list.add(integer)
      }

    }
    list
  }
}