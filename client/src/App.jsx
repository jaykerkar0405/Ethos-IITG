import "./App.css";
import Chat from "./components/chat";
import Loading from "./components/loading";
import Sidebar from "./components/sidebar";
import { useEffect, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";

function App() {
  const [chat, set_chat] = useState({
    chat_id: 0,
    username: "",
  });
  const { user, isLoading, isAuthenticated, loginWithRedirect } = useAuth0();

  // Handle authentication and redirect if not authenticated
  useEffect(() => {
    if (!isLoading && !isAuthenticated) {
      loginWithRedirect();
    }
  }, [isLoading, isAuthenticated, loginWithRedirect]);

  // Handle user data fetching
  useEffect(() => {
    if (isAuthenticated) {
      const fetchUserData = async () => {
        try {
          const check_username = await fetch(
            `${import.meta.env.VITE_SERVER_BASE_URL}/api/Appuser/${
              user.nickname
            }`
          );

          if (check_username.status !== 302) {
            const headers = new Headers();
            headers.append("Content-Type", "application/json");

            const request_options = {
              method: "POST",
              headers: headers,
              body: JSON.stringify({
                name: user.name,
                username: user.nickname,
              }),
            };

            await fetch(
              `${import.meta.env.VITE_SERVER_BASE_URL}/api/Appuser/`,
              request_options
            );
          }
        } catch (error) {
          console.log("Error: ", error);
        }
      };

      fetchUserData();
    }

    // No cleanup is needed here as there's no subscription
  }, [isAuthenticated, user]);

  // Show loading indicator
  if (isLoading) {
    return <Loading />;
  }

  return (
    <div className="container flex w-full h-screen justify-between items-center">
      <Sidebar user={user} set_chat={set_chat} />
      <Chat user={user} chat={chat} />
    </div>
  );
}

export default App;
