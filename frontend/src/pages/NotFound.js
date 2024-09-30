import { Link } from "react-router-dom";

const NotFound = () => {
  return (
    <div>
      <div>Path not found</div>
      <Link to="/">Go to Home Page</Link>
    </div>
  );
};

export default NotFound;