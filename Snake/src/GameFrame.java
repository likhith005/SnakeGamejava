import javax.swing.*;

//Swing is a Java GUI (Graphical User Interface) toolkit
// that provides a set of classes and components for creating desktop applications with graphical user interfaces.
// It is part of the Java Foundation Classes (JFC) and is designed to be platform-independent,
// meaning that Swing applications can run on different operating systems without major modifications.

//The class myFrame extends the class JFrame . The paint() method of a JFrame object is called by the Java system
// (not by you) to finish the display. Most of the graphic is done; paint() just finishes it.
// If you override the paint() method, you can display your own components in the frame.
//JFrame:
//JFrame is a class that represents a top-level container window in a Java GUI application.
// It serves as the main window of the application and can hold other GUI components
// such as buttons, labels, text fields, etc.
// A JFrame provides the basic structure and functionality for your application's graphical interface,
// including the title bar, window decorations, and control buttons like minimize, maximize, and close.
public class GameFrame extends JFrame {
    GameFrame(){
        //GamePanel panel = new GamePanel();
        //new GamePanel();
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.isResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void isResizable(boolean b) {
    }
}

