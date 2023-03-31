import React, {useState} from "react";
import {AppBar, Toolbar, Typography, Container, Grid, Paper, IconButton} from "@material-ui/core";
import {makeStyles} from "@material-ui/core/styles";
import GridComponent from "./GridComponent";

const useStyles = makeStyles((theme) => ({
    appBar: {
        marginBottom: theme.spacing(4),
    },
    container: {
        flexGrow: 1,
    },
    paper: {
        padding: theme.spacing(2),
        textAlign: "center",
        color: theme.palette.text.secondary,
    },
}));

const Dashboard = (props) => {
    const classes = useStyles();

    const handleLogout = () => {
        // Actualizar el estado de autenticación
        props.handleLogout();
    };

    return (
        <>
            <AppBar position="static" className={classes.appBar}>
                <Toolbar>
                    <Typography variant="h6">for-devs.com</Typography>
                    <IconButton color="inherit" onClick={handleLogout}>
                        exit
                    </IconButton>
                </Toolbar>
            </AppBar>

            <Container maxWidth="lg" className={classes.container}>
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <Paper className={classes.paper}>
                            <GridComponent/></Paper>
                    </Grid>
                </Grid>
            </Container>

        </>
    );
};

export default Dashboard;