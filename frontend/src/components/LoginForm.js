import {useState} from "react";
import {
    Button,
    TextField,
    Grid,
    Paper,
    Typography,
} from "@material-ui/core";

function LoginForm(props) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [buttonClicked, setButtonClicked] = useState(false);

    const handleClick = (event) => {
        event.preventDefault();
        setButtonClicked(true);

        // Aquí va el código para realizar la petición al backend

        if (username && password) {
            props.handleLogin(true);
        }
    };

    const handleInputChange = (event) => {
        const {name, value} = event.target;
        name === "username" ? setUsername(value) : setPassword(value);
    };

    const isButtonDisabled = !username || !password || buttonClicked;

    return (
        <Grid container>
            <Grid
                item
                xs={12}
                sm={8}
                md={5}
                component={Paper}
                elevation={6}
                square
            >
                <div className="paper" >
                    <Typography component="h1" variant="h5">
                        Login
                    </Typography>
                    <form>
                        <TextField
                            variant="outlined"
                            margin="normal"
                            required
                            fullWidth
                            id="username"
                            label="Username"
                            name="username"
                            autoComplete="username"
                            autoFocus
                            value={username}
                            onChange={handleInputChange}
                        />
                        <TextField
                            variant="outlined"
                            margin="normal"
                            required
                            fullWidth
                            name="password"
                            label="Password"
                            type="password"
                            id="password"
                            autoComplete="current-password"
                            value={password}
                            onChange={handleInputChange}
                        />
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            color="primary"
                            className="submit"
                            onClick={handleClick}
                            disabled={isButtonDisabled}
                        >
                            Sign In
                        </Button>
                    </form>
                </div>
            </Grid>
        </Grid>
    );
}

export default LoginForm;
