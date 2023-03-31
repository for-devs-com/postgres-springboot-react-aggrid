import {AgGridReact} from "ag-grid-react";
import React, {useCallback, useEffect, useMemo, useRef, useState} from "react";
import StudentDataService from "../services/StudentDataService";
import {Button} from "@material-ui/core";
import {FormDialog} from "./FormDialog";


function GridComponent() {

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


    // Export as excel
    const onBtExport = () => {
        gridApi.exportDataAsExcel();
    }

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


    return (
        <div>
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

            { /*create student and export as excel buttons*/}
        <div>
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
        </div>

        {/*Dialog component*/}
        <FormDialog
            openDialogForm={openDialogForm}
            handleClose={handleClose}
            data={formState}
            onChange={handleInputChange}
            handleFormSubmit={handleFormSubmit}
            switchState={handleCheckStatusChange}
        />

   </div>

)
    ;

}

export default GridComponent;