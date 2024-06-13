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
import javafx.scene.input.MouseEvent;

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

    int id = 0;

    @FXML
    private TextField uCourse;

    @FXML
    private TextField uFname;

    @FXML
    private TextField uLname;

    void clear() {
        uFname.setText(null);
        uLname.setText(null);
        uCourse.setText(null);
        btnSave.setDisable(false);
    }

    @FXML
    void clearField(ActionEvent event) {
        clear();
    }

    @FXML
    void createStudent(ActionEvent event) {
        String query = "insert into students(FirstName, LastName, COURSE) values(?, ?, ?)";
        con = DB_Connection.getConnection();
        try {
            st = con.prepareStatement(query);
            st.setString(1, uFname.getText());
            st.setString(2, uLname.getText());
            st.setString(3, uCourse.getText());
            st.executeUpdate();
            clear();
            showStudents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void getData(MouseEvent event) {
        Student student = table.getSelectionModel().getSelectedItem();
        id = student.getId();
        uFname.setText(student.getFirstName());
        uLname.setText(student.getLastName());
        uCourse.setText(student.getCourse());
        btnSave.setDisable(true);
    }

    @FXML
    void deleteStudent(ActionEvent event) {
        String delete = "delete from students where id = ?";
        con = DB_Connection.getConnection();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
            showStudents();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void updateStudent(ActionEvent event) {
        String update = "update students set FirstName = ?, LastName = ?, Course = ? where id = ?";
        con = DB_Connection.getConnection();
        try {
            st = con.prepareStatement(update);
            st.setString(1, uFname.getText());
            st.setString(2, uLname.getText());
            st.setString(3, uCourse.getText());
            st.setInt(4, id);
            st.executeUpdate();
            clear();
            showStudents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
