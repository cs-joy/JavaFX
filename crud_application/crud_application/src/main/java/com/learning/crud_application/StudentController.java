package com.learning.crud_application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Student, String> colCourse;

    @FXML
    private TableColumn<Student, String> colFname;

    @FXML
    private TableColumn<Student, String> colLname;

    @FXML
    private TableColumn<Student, Integer> colid;

    @FXML
    private TableView<Student> table;

    @FXML
    private TextField uCourse;

    @FXML
    private TextField uFname;

    @FXML
    private TextField uLname;

    @FXML
    void clearField(ActionEvent event) {

    }

    @FXML
    void createStudent(ActionEvent event) {

    }

    @FXML
    void deleteStudent(ActionEvent event) {

    }

    @FXML
    void updateStudent(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showStudents();
    }

    public ObservableList<Student> getStudents() {
        ObservableList<Student> students = FXCollections.observableArrayList();

        String query = "select * from students";
        con = DB_Connection.getConnection();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setFirstName(rs.getString("FirstName"));
                student.setLastName(rs.getString("LastName"));
                student.setCourse(rs.getString("course"));
                students.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return students;
    }

    public void showStudents() {
        ObservableList<Student> list = getStudents();
        table.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));

        colFname.setCellValueFactory(new PropertyValueFactory<Student, String>("FirstName"));
        colLname.setCellValueFactory(new PropertyValueFactory<Student, String>("LastName"));
        colCourse.setCellValueFactory(new PropertyValueFactory<Student, String>("course"));
    }
}
