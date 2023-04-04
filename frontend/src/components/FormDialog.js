import React from "react";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import {FormControlLabel, FormGroup, InputAdornment, Switch, TextField} from "@material-ui/core";

function FormDialog({
                        openDialogForm,
                        handleClose,
                        data,
                        onChange,
                        handleFormSubmit,
                        switchState,
                    }) {

    const {id, fullName, email, phone, birthDate, isActive} = data;


    return (
        <div>
            <Dialog
                open={openDialogForm}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >

                <DialogTitle id="alert-dialog-title">
                    {id ? "Update Student" : "Create new student"}
                </DialogTitle>

                <DialogContent>

                    <form>

                        <TextField
                            id="fullName"
                            value={fullName}
                            onChange={(e) => onChange(e)}
                            placeholder="Enter firstName"
                            label="Name"
                            variant="outlined"
                            margin="dense"
                            fullWidth
                            type="text"
                            InputProps={{
                                startAdornment: (
                                    <InputAdornment position="start">
                                        <span>Name: </span>
                                    </InputAdornment>
                                ),
                            }}
                            inputProps={{
                                pattern: "[A-Za-z]+( [A-Za-z]+)*",
                                title: "Debe ingresar solo letras y espacios",
                            }}
                        />

                        <TextField
                            id="email"
                            value={email}
                            onChange={(e) => onChange(e)}
                            placeholder="Enter email"
                            label="Email"
                            variant="standard"
                            margin="dense"
                            fullWidth
                            type="email"
                            inputProps={{
                                pattern: "[^@]+@[^@]+\\.[^@]+",
                                title: "Debe ingresar un correo electrónico válido",}}
                        />

                        <TextField
                            id="phone"
                            value={phone}
                            onChange={(e) => onChange(e)}
                            placeholder="Enter phone number"
                            label="Phone Number"
                            variant="outlined"
                            margin="dense"
                            fullWidth
                        />

                        <TextField
                            id="birthDate"
                            value={birthDate}
                            onChange={(e) => onChange(e)}
                            placeholder="Enter Date of birth"
                            label="Date of Birth"
                            variant="outlined"
                            margin="dense"
                            fullWidth
                            inputProps={{
                                pattern: "\\d{4}-\\d{2}-\\d{2}",
                                title:
                                    "Debe ingresar la fecha en formato YYYY-MM-DD",
                            }}
                        />

                        <FormGroup>
                            <FormControlLabel
                                control={
                                    <Switch
                                        id="switchStatus"
                                        checked={isActive}
                                        onChange={switchState}
                                        inputProps={{'aria-label': 'controlled'}}
                                    />
                                }
                                label="Status"
                            ></FormControlLabel>
                        </FormGroup>
                    </form>
                </DialogContent>

                <DialogActions>

                    <Button onClick={handleClose} color="secondary" variant="outlined">
                        Cancel
                    </Button>

                    <Button
                        color="primary"
                        onClick={() => handleFormSubmit()}
                        variant="contained"
                    >
                        {id ? "Update" : "Submit"}
                    </Button>
                </DialogActions>

            </Dialog>
        </div>
    );
}

export {FormDialog};
