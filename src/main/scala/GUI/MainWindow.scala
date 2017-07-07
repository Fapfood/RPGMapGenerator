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
    title = "RPGMapGenerator3000"
    menuBar = new MenuBar {
      contents += new Menu("Menu") {
        contents += new MenuItem(Action("Randomize") {
          Controller.randomize()
        })
        contents += new MenuItem(Action("Generate") {
          Controller.generate()
        })
      }
    }

    contents = getTextFieldsPanel
    // size = new Dimension(500,500)
    centerOnScreen()
  }

  private[this] def getTextFieldsPanel: BoxPanel = {
    new BoxPanel(Orientation.Vertical) {

      contents += getLabelAndTextField("Map Width: ", WidthTextField)
      contents += getLabelAndTextField("Map Height: ", HeightTextField)
      contents += getLabelAndTextField("Trees Number: ", TreesTextField)
      contents += getLabelAndTextField("Houses number: ", HousesTextField)
      contents += getLabelAndTextField("Exits number: ", ExitsTextField)
    }
  }

  private[this] def getLabelAndTextField(label: String, textField: TextField): Panel = {
    import BorderPanel.Position._
    new BorderPanel() {
      layout += new Label(label) -> West
      layout += textField -> East
    }
  }

  private[this] def getFilteredTextField: TextField = {
    var tField: TextField = new TextField(columns = 10) {
      listenTo(keys)
      reactions += { case e: KeyTyped =>
        if (!e.char.isDigit) e.consume
      }
    }
    tField.text = "0"
    tField
  }
}