import React, {useState, useEffect} from "react";
import "./App.css";
import {AgGridReact} from "ag-grid-react";
import "ag-grid-community/dist/styles/ag-grid.css";
import "ag-grid-community/dist/styles/ag-theme-material.min.css";
import {Grid, Button} from "@material-ui/core";
import FormDialog from "./components/dialog";
import StudentDataService from "./services/StudentDataService";
//import axios from "axios";

// initial data values in form 
const initialValue = {fullName: "", email: "", phone: "", dob: ""};

// Main Function
function App(message) {
    // eslint-disable-next-line
    const [gridApi, setGridApi] = useState(null);
    const [tableData, setTableData] = useState(null);
    const [open, setOpen] = React.useState(false);
    const [formData, setFormData] = useState(initialValue);

    // add user button popup form onClick event
    const handleClickOpen = () => {
        setOpen(true);
    };

    // form close
    const handleClose = () => {
        setOpen(false);
        setFormData(initialValue);
    };

    const columnDefs = [
        {headerName: "ID", field: "studentID"},
        {headerName: "Name", field: "fullName"},
        {headerName: "Email", field: "email"},
        {headerName: "phone", field: "phone"},
        {headerName: "Date of Birth", field: "dob"},
        {headerName: "State", field: "isActive"},
        {headerName: "Department", field: "deptName"},
        {headerName: "Subject", field: "subjectLearningName" },
        {
            headerName: "Actions", field: "student_id",
            cellRendererFramework: (params) =>
                <div>
                    <Button
                        variant="outlined"
                        color="primary"
                        onClick={() => handleUpdate(params.data)}
                    >
                        Update
                    </Button>

                    <Button
                        variant="outlined"
                        color="secondary"
                        onClick={() => handleDelete(params.value)}
                    >
                        Delete
                    </Button>
                </div>

        }
    ];


    //  first time getUsers
    useEffect(() => {
        getUsers();
    }, []);

    // Fetching user data from server
    const getUsers = () => {
        StudentDataService.getAll()
            .then((response) => {
                console.log(response.data)
                setTableData(response.data)
            })
    };

    // updating data and opening pop up window
    const handleUpdate = (oldData) => {
        setFormData(oldData);
        handleClickOpen();
    };

    //deleting a user
    const handleDelete = (id) => {
        console.log('print id:', id)
        // delete confirmation message
        const confirmation = window.confirm(
            "Are you sure, you want to delete this row",
            id
        )

        if (confirmation) {
            StudentDataService.delete(id).then((r) => r.data).then((r) => {
                handleClose();
                getUsers();
            })
        }
    };

    const handleFormSubmit = () => {
        if (formData.id) {


            // Alert message
            const confirm = window.confirm(
                "Are you sure, you want to update this row ?"
            );

            //  updating a user
            confirm && StudentDataService.update(formData.id)
                .then((r) => r.data)
                .then(r => {
                    setFormData(r.data)
                    handleClose();
                    getUsers()
                })
            // fetch(url + `/${formData.id}`, {
            //     method: "PUT",
            //     body: JSON.stringify(formData),
            //     headers: {
            //         "content-type": "application/json",
            //     },
            // })
            //     .then((resp) => resp.data)
            //     .then((resp) => {
            //         handleClose();
            //         getUsers();
            //     });
        } else {

            // adding new user
            StudentDataService.create(formData).then((r) => r.data).then((r) => {
                //setFormData(r.data)
                handleClose();
                getUsers()
            })

            // fetch(url, {
            //     method: "POST",
            //     body: JSON.stringify(formData),
            //     headers: {
            //         "content-type": "application/json",
            //     },
            // })
            //     .then((resp) => resp.data)
            //     .then((resp) => {
            //         handleClose();
            //         getUsers();
            //     });
        }
    };

    // onChange event in dialog form
    const onChange = (e) => {
        const {value = '', id} = e.target;
        setFormData({...formData, [id]: value});
        console.log(value, id)
    };

    // render grid when data exist
    const onGridReady = (params) => {
        setGridApi(params);
    };

    const defaultColDef = {
        sortable: true,
        flex: 1,
        filter: true,
        floatingFilter: true,
    };

    return (

        <div className="App">

            <h1 align="center">for-devs.com</h1>
            <h2>React, AgGrid, Material UI, Spring Boot, Data JPA, PostgresSQL,And Maven Example Application</h2>

            {/* Material UI Grid Layout */}
            <Grid align="right">
                <Button variant="contained" color="primary" onClick={handleClickOpen}>
                    Create User
                </Button>
            </Grid>

            <div className="ag-theme-material" style={{height: "500px"}}>
                <AgGridReact
                    rowData={tableData}
                    columnDefs={columnDefs}
                    defaultColDef={defaultColDef}
                    onGridReady={onGridReady}
                />
            </div>

            <FormDialog
                open={open}
                handleClose={handleClose}
                data={formData}
                onChange={onChange}
                handleFormSubmit={handleFormSubmit}
            />
        </div>
    );
}

export default App;
