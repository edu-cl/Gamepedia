module com.Eduardo.Gamepedia {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires java.xml.bind;
	requires com.sun.xml.bind;
	requires mysql.connector.java;
	requires java.desktop;
	requires javafx.base;

	




	



    opens com.Eduardo.Gamepedia to javafx.fxml;
    opens com.Eduardo.Gamepedia.Model.Connection to com.sun.xml.bind, java.xml.bind, javafx.baseEmpty;
    opens com.Eduardo.Gamepedia.Utils to java.xml.bind, com.sun.xml.bind, javafx.baseEmpty;
    exports com.Eduardo.Gamepedia;
   
}