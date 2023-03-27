import React from "react";
import { AppBar, Toolbar, Typography, Container, Grid, Paper } from "@material-ui/core";
import { makeStyles } from "@material-ui/core/styles";

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

const Dashboard = () => {
    const classes = useStyles();

    return (
        <>
            <AppBar position="static" className={classes.appBar}>
                <Toolbar>
                    <Typography variant="h6">for-devs.com</Typography>
                </Toolbar>
            </AppBar>

            {/* heading */}
            <Grid item lg={12}>
                <h2>React, AgGrid, Material UI, Spring Boot, Data JPA, PostgresSQL,And Maven Example
                    Application</h2>
            </Grid>
        </>
    );
};

export default Dashboard;