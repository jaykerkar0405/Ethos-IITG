import "./App.css";
import Loading from "./components/loading";
import { useAuth0 } from "@auth0/auth0-react";

function App() {
  const { user, isLoading, isAuthenticated, loginWithRedirect } = useAuth0();

  if (isLoading) {
    return <Loading />;
  }

  if (!isLoading && !isAuthenticated) {
    loginWithRedirect();
  }

  return (
    isAuthenticated && (
      <div>
        <img src={user.picture} alt={user.name} />
        <h2 className="text-red-500">{user.name}</h2>
        <p>{user.email}</p>
      </div>
    )
  );
}

export default App;
