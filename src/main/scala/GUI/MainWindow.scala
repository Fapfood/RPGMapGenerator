import GUI.Controller

import scala.swing._

object MainWindow extends SimpleSwingApplication {
  val WidthTextField: TextField = new TextField(columns = 10)
  val HeightTextField: TextField = new TextField(columns = 10)
  val TreesTextField: TextField = new TextField(columns = 10)
  val HousesTextField: TextField = new TextField(columns = 10)
  val ExitsTextField: TextField = new TextField(columns = 10)
  def top = new MainFrame {
    title = "First Swing App"
    menuBar = new MenuBar {
      contents += new Menu("Menu"){
        contents += new MenuItem(Action("Losuj dane"){
          Controller.randomize
        })
        contents += new MenuItem(Action("Generuj"){
          Controller.generate
        })
        contents += new MenuItem(Action("Zapisz"){
          Controller.save
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

}