import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { register } from "../api/auth";
import { doLogin } from "../redux/authSlice";
import { useAppDispatch, useAppSelector } from "../redux/store";

export default function RegisterPage() {
    const navigate = useNavigate();
    const dispatch = useAppDispatch();
    const isLoggedIn = useAppSelector(state => state.auth.isLoggedIn);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [firstname, setFirstname] = useState("");
    const [lastname, setLastname] = useState("");
    const [role, setRole] = useState("USER");
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
            const data = await register(firstname, lastname, email, password, role);
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
                    placeholder="First Name"
                    value={firstname}
                    onChange={e => setFirstname(e.target.value)}
                />
                <input
                    placeholder="Last Name"
                    value={lastname}
                    onChange={e => setLastname(e.target.value)}
                />
                <input
                    placeholder="Role"
                    value={role}
                    onChange={e => setRole(e.target.value)}
                />
                <input
                    placeholder="Email"
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                />
                <input
                    placeholder="Password"
                    type="password"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                />
                {error && <p>{error}</p>}
                <button onClick={handleLogin} >Login</button>
                <p>Already have an account? <Link to="/login">Login</Link></p>
            </div>
        </div>
    );
}