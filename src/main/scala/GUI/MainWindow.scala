import GUI.Controller

import scala.swing._

object MainWindow extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "First Swing App"
    contents = new Button {
      text = "Click me"
    }
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
    size = new Dimension(500,500)
    centerOnScreen()
  }

}