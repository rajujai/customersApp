import {
  createBrowserRouter,
  Navigate,
  RouterProvider,
} from "react-router-dom";
import "./App.css";
import AddCustomer from "./pages/AddCustomer";
import Customers from "./pages/Customers";
import EditCustomer from "./pages/EditCustomer";
import Login from "./pages/Login";
import NotFound from "./pages/NotFound";
import Register from "./pages/Register";
import { useAppSelector } from "./redux/store";

function App() {
  const loggedIn = useAppSelector((state) => state.auth.loggedIn);

  const privateRoutes = [
    {
      path: "/",
      element: <Navigate to="/customers" />
    },
    {
      path: "/customers",
      element: <Customers />
    },
    {
      path: "/customers/add",
      element: <AddCustomer />
    },
    {
      path: "/customers/edit",
      element: <EditCustomer />
    },
  ]

  const publicRoutes = [
    {
      path: "/login",
      element: <Login />,
    },
    {
      path: "/register",
      element: <Register />,
    },
    {
      path: "*",
      element: <NotFound />,
    },
  ]

  const PrivateRoute = ({ isAuthenticated, children }) => {
    return isAuthenticated ? children : <Navigate to="/login" />;
  };

  const wrappedPrivateRoutes = privateRoutes.map((route) => ({
    ...route,
    element: (
      <PrivateRoute isAuthenticated={loggedIn}>
        {route.element}
      </PrivateRoute>
    ),
  }));
  const router = createBrowserRouter([
    ...wrappedPrivateRoutes,
    ...publicRoutes
  ]);
  return (
    <>
      <RouterProvider router={router} />
    </>
  );
}

export default App;