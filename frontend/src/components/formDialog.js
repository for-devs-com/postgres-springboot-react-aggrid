import React from "react";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import {/*FormControlLabel,*/ FormGroup, Switch, TextField} from "@material-ui/core";

function FormDialog({
                        open,
                        handleClose,
                        data,
                        onChange,
                        handleFormSubmit,
                        handleCheckStatusChange,
                        checked
                    }) {

    const {user_id, fullName, email, phone, birthDate, isActive} = data;






    return (
        <div>
            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >

                <DialogTitle id="alert-dialog-title">
                    {user_id ? "Update user" : "Create new user"}
                </DialogTitle>

                <DialogContent>

                    <form>

                        <TextField
                            id="fullName"
                            value={fullName}
                            onChange={(e) => onChange(e)}
                            placeholder="Enter fullName"
                            label="Name"
                            variant="outlined"
                            margin="dense"
                            fullWidth
                            type="text"
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
                        />

                        <FormGroup>
                            {/*<FormControlLabel*/}
                            {/*label="Status"
                                control={*/}
                            <Switch
                                id="switchStatus"
                                checked={checked}
                                onChange={handleCheckStatusChange}
                                inputProps={{'aria-label': 'controlled'}}
                                value={data.isActive}


                            />{/*} />*/}
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
                        {user_id ? "Update" : "Submit"}
                    </Button>
                </DialogActions>

            </Dialog>
        </div>
    );
}

export {FormDialog};
