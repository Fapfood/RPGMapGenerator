package GUI

import scala.swing._
import scala.swing.event.KeyTyped

object MainWindow extends SimpleSwingApplication {
  val WidthTextField: TextField = getFilteredTextField
  val HeightTextField: TextField = getFilteredTextField
  val TreesTextField: TextField = getFilteredTextField
  val HousesTextField: TextField = getFilteredTextField
  val ExitsTextField: TextField = getFilteredTextField
  def top = new MainFrame {
    title = "First Swing App"
    menuBar = new MenuBar {
      contents += new Menu("Menu"){
        contents += new MenuItem(Action("Losuj dane"){
          Controller.randomize()
        })
        contents += new MenuItem(Action("Generuj"){
          Controller.generate()
        })
      }
    }

    import BorderPanel.Position._
    contents = getTextFieldsPanel
   // size = new Dimension(500,500)
    centerOnScreen()
  }

  private[this] def getTextFieldsPanel: BoxPanel = {
    new BoxPanel(Orientation.Vertical){

      contents += getLabelAndTextField("Map Width: ", WidthTextField)
      contents += getLabelAndTextField("Map Height: ", HeightTextField)
      contents += getLabelAndTextField("Trees Number: ", TreesTextField)
      contents += getLabelAndTextField("Houses number: ", HousesTextField)
      contents += getLabelAndTextField("Exits number: ", ExitsTextField)
    }
  }

  private[this] def getLabelAndTextField(label: String, textField: TextField): Panel = {
    new FlowPanel(){
      contents += new Label(label)
      contents += textField
    }
  }

  private[this] def getFilteredTextField: TextField = {
    var tField : TextField = new TextField (columns = 10){
      listenTo(keys)
      reactions += { case e: KeyTyped =>
        if (!e.char.isDigit) e.consume
      }
    }
    tField.text = "0"
    tField
  }

}