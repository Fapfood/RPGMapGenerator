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
        contents += new MenuItem(Action("Zapisz"){
          Controller.save()
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
      contents += new FlowPanel(){
        contents += new Label("Map Width: ")
        contents += WidthTextField
      }

      contents += new FlowPanel(){
        contents += new Label("Map Height: ")
        contents += HeightTextField
      }

      contents += new FlowPanel(){
        contents += new Label("Trees Number: ")
        contents += TreesTextField
      }

      contents += new FlowPanel(){
        contents += new Label("Houses number: ")
        contents += HousesTextField
      }

      contents += new FlowPanel(){
        contents += new Label("Exits number: ")
        contents += ExitsTextField
      }

    }
  }

  private[this] def getFilteredTextField: TextField = {
    new TextField (columns = 10){
      listenTo(keys)
      reactions += { case e: KeyTyped =>
        if (!e.char.isDigit) e.consume
      }
    }
  }

}