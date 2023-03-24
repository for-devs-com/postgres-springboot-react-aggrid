import React, {useEffect, useMemo, useState} from "react";
import "./App.css";
import {AgGridReact} from "ag-grid-react";
import 'ag-grid-enterprise';
import "ag-grid-community/dist/styles/ag-grid.css";
import "ag-grid-community/dist/styles/ag-theme-material.min.css";
import {Button, Container, Grid} from "@material-ui/core";
import {FormDialog} from "./components/formDialog";
import StudentDataService from "./services/StudentDataService";

// initial data values in form 
const initialValue = {fullName: "", email: "", phone: "", birthDate: "", isActive: ""};

// Main Function
function App(message) {
    // eslint-disable-next-line
    const [tableData, setTableData] = useState(null);
    const [open, setOpen] = React.useState(false);
    const [formState, setFormState] = useState(initialValue)
    const [gridApi, setGridApi] = useState();


    // add user button popup form onClick event
    const handleClickOpen = () => {
        setOpen(true);
    };

    // form close
    const handleClose = () => {
        setOpen(false);
        setFormState(initialValue);
    };

    //
    const defaultColDef = useMemo(() => {
        return {
            editable: true, sortable: true, flex: 1, filter: true, resizable: true,
        };
    }, []);

    const [columnDefs, setColumnsDefs] = useState([
        {headerName: "ID", field: "studentID"}, {
            headerName: "Name",
            field: "fullName"
        }, {headerName: "Email", field: "email"}, {headerName: "Phone", field: "phone"}, {
            headerName: "Date of Birth",
            field: "birthDate"
        }, {headerName: "State", field: "isActive"}, {
            headerName: "Department",
            field: "department"
        }, {headerName: "Subject", field: "subjectLearning"}, {
            headerName: "Actions", field: "studentID",
            cellRendererFramework: (params) => <div>
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

        }]);


    //  first time getStudents
    useEffect(() => {
        getStudents();
    }, []);

    // Fetching user data from server
    const getStudents = () => {
        StudentDataService.getAll()
            .then((response) => {
                console.log("getStudent Response: ", response.data)
                setTableData(response.data)
            })
    };

    // updating data and opening pop up window
    const handleUpdate = (oldData) => {
        setFormState(oldData);
        handleClickOpen();
    };

    //deleting a user
    const handleDelete = (id) => {
        console.log('handleDelete Content:', id);
        // delete confirmation message
        const confirmation = window.confirm("Are you sure, you want to delete this row?")

        if (confirmation) {
            StudentDataService.delete(id).then((r) => r.data).then((r) => {
                handleClose();
                getStudents();
            })
        }
    };

    const handleFormSubmit = () => {
        if (formState) {


            // Alert message
            const confirm = window.confirm("Are you sure, you want to update this row ?");

            //  updating a user
            confirm && StudentDataService.update(formState.id)
                .then((r) => r.data)
                .then(r => {
                    setFormState(r.data)
                    handleClose();
                    getStudents()
                })
        } else {

            // adding new user
            StudentDataService.create(formState).then((r) => r.data).then((r) => {
                handleClose();
                getStudents()
            })

        }
    };

    // handleInputChange event in dialog form
    const handleInputChange = (event) => {
        const {value = '', id} = event.target;
        setFormState({...formState, [event.target.id]: event.target.value});
        console.log(value, id)
    };

    const handleCheckStatusChange = (event) => {
        setChecked(event.target.checked);
    };

    /*Handling check state*/
    const [checked, setChecked] = React.useState(true);


    // render grid when data exist
    const onGridReady = (params) => {
        setGridApi(params.api)
    };

    // Export as excel
    const onBtExport = () => {
        // gridRef.current.api.exportDataAsExcel();
        gridApi.exportDataAsExcel();
    }

    return (

        <div className="App">
            <Container maxWidth="xl">


                <h1 align="center">for-devs.com</h1>
                <h2>React, AgGrid, Material UI, Spring Boot, Data JPA, PostgresSQL,And Maven Example Application</h2>

                {/* Material UI Grid Layout */}

                <div className="ag-theme-alpine" style={{height: 550}}>
                    <AgGridReact
                        rowData={tableData}
                        columnDefs={columnDefs}
                        defaultColDef={defaultColDef}
                        onGridReady={onGridReady}
                        pagination={true}
                        paginationPageSize={10}
                    />
                </div>


                {/*create student and export as excel buttons*/}
                <Grid align="center">

                    <Button variant="contained" color="primary" onClick={handleClickOpen}>
                        Create Student
                    </Button>

                    <Button
                        variant="contained"
                        color="secondary"
                        onClick={onBtExport}
                    >
                        Export As Excel
                    </Button>
                </Grid>


                {/*Dialog component*/}
                <FormDialog
                    open={open}
                    handleClose={handleClose}
                    data={formState}
                    onChange={handleInputChange}
                    handleFormSubmit={handleFormSubmit}
                    handleCheckStatusChange={handleCheckStatusChange}
                    checked={checked}
                />

            </Container>
        </div>);
}

export default App;
