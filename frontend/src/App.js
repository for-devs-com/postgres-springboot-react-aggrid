import React, {useCallback, useEffect, useMemo, useRef, useState} from "react";
import "./App.css";
import {AgGridReact} from "ag-grid-react";
import "ag-grid-enterprise";
import "ag-grid-community/dist/styles/ag-grid.css";
import "ag-grid-community/dist/styles/ag-theme-alpine.css";
import "ag-grid-community/dist/styles/ag-theme-material.min.css";
import {Button, Grid} from "@material-ui/core";
import {FormDialog} from "./components/FormDialog";
import StudentDataService from "./services/StudentDataService";
import Dashboard from "./components/dashboard";


// Main Function
function App() {


    // initial data values in form
    const initialValue = {fullName: "", email: "", phone: "", birthDate: "", isActive: false};
    const gridRef = useRef();
    const [gridApi, setGridApi] = useState();
    const [tableData, setTableData] = useState();
    const [openDialogForm, setOpen] = React.useState(false);
    const [formState, setFormState] = useState(initialValue)


    // add user button popup form onClick event
    const handleClickOpen = () => {
        setOpen(true);
    };

    // form close
    const handleClose = () => {
        setOpen(false);
        setFormState(initialValue);
    };

    // default column definition
    const defaultColDef = useMemo(() => {
        return {
            // set the default column width
            width: 150,
            // make every column editable
            editable: true,
            // make every column use 'text' filter by default
            filter: 'agTextColumnFilter',
            // enable floating filters by default
            floatingFilter: true,
            // make columns resizable
            resizable: true,
        };
    }, []);

    // column definitions for grid
    const [columnDefs, setColumnsDefs] = useState([
        {headerName: "ID", field: "studentID"},
        {headerName: "Name", field: "fullName"},
        {headerName: "Email", field: "email"},
        {headerName: "Phone", field: "phone"},
        {headerName: "Date of Birth", field: "birthDate"},
        {headerName: "State", field: "isActive"},
        {
            headerName: "Actions", field: "studentID",

            cellRendererFramework: (params) =>
                <div>
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

    // Fetching students data from server
    const getStudents = () => {
        StudentDataService.getAll()
            .then((response) => {
                console.log("getStudent Response: ", response.data)
                setTableData(response.data)
            })
    };

    //deleting a user
    const handleDelete = (id) => {
        console.log('handleDelete Content:', id);
        // delete confirmation message
        const confirmation = window.confirm("Are you sure, you want to delete this row?" + id);

        if (confirmation) {
            StudentDataService.delete(id).then((r) => r.data).then((r) => {
                handleClose();
                getStudents();
            })
        }
    };

    // handleFormSubmit event in dialog form
    const handleFormSubmit = () => {
        if (formState.id) {


            // Alert message
            const confirm = window.confirm("Are you sure, you want to update this row ?" + formState.id);

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
        setFormState({...formState, [id]: value});
        console.log(value, id)
    };

    /*Handling check state*/
    const handleCheckStatusChange = (event) => {
        const {checked} = event.target;
        setFormState({...formState, isActive: checked})
    };

    // render grid when data exist
    const onGridReady = (params) => {
        setGridApi(params.api)

    };

    // Handling cell value changes
    const handleCellValueChanged = useCallback(async (params) => {
        const {data} = params;
        const updatedData = {...data, [params.colDef.field]: params.newValue};

        try {
            await StudentDataService.update(data.studentID, updatedData);
            getStudents();
        } catch (error) {
            console.error("Error updating cell value: ", error);
        }
    }, [getStudents]);


    // Export as excel
    const onBtExport = () => {
        gridApi.exportDataAsExcel();
    }

    return (

        <div className="App">
            <Dashboard/>


            <Grid maxWidth="lg" align="center" container spacing={2}>

                {/* Material UI Grid Layout */}
                <Grid item lg={12}>
                    <div className="ag-theme-alpine" style={{height: 540, maxWidth: 1420}}>
                        <AgGridReact
                            rowData={tableData}
                            columnDefs={columnDefs}
                            defaultColDef={defaultColDef}
                            onGridReady={onGridReady}
                            ref={gridRef}
                            pagination={true}
                            paginationPageSize={10}
                            onCellValueChanged={handleCellValueChanged}
                        ></AgGridReact>
                    </div>
                </Grid>


                {/*create student and export as excel buttons*/}
                <Grid item lg={12}>
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
                    openDialogForm={openDialogForm}
                    handleClose={handleClose}
                    data={formState}
                    onChange={handleInputChange}
                    handleFormSubmit={handleFormSubmit}
                    switchState={handleCheckStatusChange}
                />

            </Grid>
        </div>)
        ;
}

export default App;
