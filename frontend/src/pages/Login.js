import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { login } from "../api/auth";
import { doLogin } from "../redux/authSlice";
import { useAppDispatch, useAppSelector } from "../redux/store";

export default function LoginPage() {
    const navigate = useNavigate();
    const dispatch = useAppDispatch();
    const isLoggedIn = useAppSelector(state => state.auth.isLoggedIn);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);
    useEffect(() => {
        if (isLoggedIn) navigate("/");
    }, [isLoggedIn, navigate]);

    const handleLogin = async () => {
        if (!email || !password) {
            setError("Please enter valid email and password");
            return;
        }
        try {
            const data = await login(email, password);
            dispatch(doLogin(data.accessToken));
            navigate("/");
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <div className="container">
            <div className="login-form">
                <input
                    type="text"
                    name="email"
                    id="email"
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                />
                <input
                    type="password"
                    name="password"
                    id="password"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                />
                {error && <p>{error}</p>}
                <button onClick={handleLogin} >Login</button>
                <p>Don't have an account? <Link to="/register">Register</Link></p>
            </div>
        </div>
    );
}