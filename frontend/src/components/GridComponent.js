import {AgGridReact} from "ag-grid-react";
import React, {useCallback, useEffect, useMemo, useRef, useState} from "react";
import StudentDataService from "../services/StudentDataService";
import {Button} from "@material-ui/core";
import {FormDialog} from "./FormDialog";
import studentDataService from "../services/StudentDataService";


/*const createServerSideDatasource = (server, page, size) => {
    return {
        getRows: async (params) => {
            console.log('[Datasource] - rows requested by grid: ', params.request);

            try {
                // Get data for the request from our server
                const response = await server(page, size);

                if (response.data && response.data.content) {
                    const rowsThisPage = response.data.content;
                    const lastRow = response.data.totalElements;

                    // Supply rows for the requested block to the grid
                    params.success({ rowData: rowsThisPage, rowCount: lastRow });
                } else {
                    params.fail();
                }
            } catch (error) {
                console.error('Error fetching data:', error);
                params.fail();
            }
        },
    };
};*/

const createServerSideDatasource = (server, page, size) => {
    return {
        getRows: (params) => {
            console.log('[Datasource] - rows requested by grid: ', params.request);
            // get data for request from our server
            server(page, size).then((response) => {
                if (response.data && response.data.content) {
                    const rowsThisPage = response.data.content;
                    const lastRow = response.data.totalElements;
                    // supply rows for requested block to grid
                    params.successCallback(rowsThisPage, lastRow);
                } else {
                    params.failCallback();
                }
            }).catch((error) => {
                console.error('Error fetching data:', error);
                params.failCallback();
            });
        },
    };
};


function GridComponent() {

    // initial data values in form
    const initialValue = {firstName: "", lastName: "", email: "", isActive: false, };
    const gridRef = useRef();
    const [gridInstance, setGridInstance] = useState(null);
    const [gridApi, setGridApi] = useState();
    const [tableData, setTableData] = useState();
    const rowModelType = 'serverSide';
    const [openDialogForm, setOpen] = React.useState(false);
    const [formState, setFormState] = useState(initialValue)
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);

    // getting pages data from server
    const getServerSideData = useCallback((newPage, newSize) => {
        StudentDataService.getAll(newPage, newSize).then((response) => {
            setTableData(response.data.content);
        });
    }, [page, size]);




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



    const getFirstStudents = useCallback(() => {
        StudentDataService.getFirstRows(size).then((response) => {
            setTableData(response.data.content);

            console.log("getFirstStudents Response: ", response.data.content)
        });
    }, [size]);

    // Fetching students data from server
    const getStudents = useCallback( () => {
        //getServerSideData();

        StudentDataService.getAll()
            .then((response) => {
                console.log("getStudent Response: ", response.data)
                setTableData(response.data)
            })
    }, []);

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
        {headerName: "First Name", field: "firstName"},
        {headerName: "LastName", field: "lastName"},
        {headerName: "Email", field: "email"},
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




    // render grid when data exist
    const onGridReady = (params) => {
        setGridInstance(params.api);
        const server = (page, size) => studentDataService.getAll(page, size);
        const createDataSource = () => {
            const datasource = createServerSideDatasource(server, page, size);
            params.api.setServerSideDatasource(datasource);
        };
        createDataSource();
    };

    // handle pagination change
    const handlePaginationChanged = useCallback(() => {
        if (!gridApi) {
            return;
        }

        const server = (page, size) => StudentDataService.getAll(page, size);
        const datasource = createServerSideDatasource(server, page, size);
        gridApi.setServerSideDatasource(datasource);
    }, [gridApi]);


    useEffect(() => {
        if (gridApi) {
            handlePaginationChanged();
        }
        //getFirstStudents();
    }, [gridInstance, handlePaginationChanged]);

    return (
        <div>
            <div className="ag-theme-alpine" style={{height: 540, maxWidth: 1420}}>
                <AgGridReact
                    rowData={tableData}
                    columnDefs={columnDefs}
                    defaultColDef={defaultColDef}
                    onGridReady={onGridReady}
                    ref={gridRef}
                    rowModelType={rowModelType}
                    pagination={true}
                    paginationPageSize={size}
                    cacheBlockSize={size}
                    onCellValueChanged={handleCellValueChanged}
                    onPaginationChanged={handlePaginationChanged}
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