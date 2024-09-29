import { useEffect, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { fetch_friends } from "../modules/sidebar";

const fetch_profile_image = (username) => {
  const identifier = username.slice(0, 1);
  return `https://cdn.auth0.com/avatars/${identifier}.png`;
};

const Loader = () => (
  <div className="flex w-full h-full justify-center items-center bg-[#111516]">
    <div className="loader" />
  </div>
);

const LogoutIcon = ({ width = 26, height = 26 }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={width}
    height={height}
    viewBox="0 0 24 24"
    fill="#908f8f"
  >
    <path d="M10 17v-2H4V7h6V5H4C2.9 5 2 5.9 2 7v10c0 1.1.9 2 2 2h6v-2H4v-2h6zM14 12h8v-2h-8V8l-4 4 4 4v-2z" />
  </svg>
);

const ChatIcon = () => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    viewBox="0 0 24 24"
    width="24"
    height="24"
    fill="currentColor"
  >
    <path d="M12 2C6.48 2 2 6.03 2 11c0 2.4 1.11 4.56 2.95 6.06-.14.59-.54 1.52-1.44 2.51a.25.25 0 0 0 .26.41c1.52-.36 3.59-1.25 4.51-1.72A10.3 10.3 0 0 0 12 20c5.52 0 10-4.03 10-9S17.52 2 12 2zm0 16c-.97 0-1.9-.15-2.78-.44-.11-.04-.24-.03-.34.03-1.13.66-2.54 1.28-3.43 1.57.55-.62 1.13-1.51 1.36-2.55.03-.14-.02-.29-.13-.38C4.88 14.79 4 13.02 4 11c0-3.86 3.58-7 8-7s8 3.14 8 7-3.58 7-8 7z" />
  </svg>
);

const Friends = ({ username }) => {
  return (
    <div className="flex items-center gap-5 bg-[#2E2E2E] hover:bg-[#1E1E1E] cursor-pointer p-3 rounded-lg mb-3">
      <div className="w-9 h-9 overflow-hidden rounded-full">
        <img
          alt={username}
          className="h-full w-auto"
          src={fetch_profile_image(username)}
        />
      </div>

      <span className="text-base font-semibold text-[#FFFFFF]">{username}</span>
    </div>
  );
};

const AddFriendModal = ({ modal, set_modal, username, set_friends }) => {
  const [friend_username, set_friend_username] = useState("");

  const submit = async (event) => {
    event.preventDefault();

    const headers = new Headers();
    headers.append("Content-Type", "application/json");

    const request_options = {
      method: "POST",
      headers: headers,
      redirect: "follow",
      body: JSON.stringify({
        username1: username,
        username2: friend_username,
      }),
    };

    const response = await fetch(
      `${import.meta.env.VITE_SERVER_BASE_URL}/api/Chat/`,
      request_options
    );

    const result = await response.json();

    if (response.status === 404) {
      alert(result.message[0]);
    } else {
      set_friends((prev_friends) => [result, ...prev_friends]);
    }

    set_friend_username("");
    set_modal(false);
  };

  return (
    <div
      className={`${
        !modal && "hidden"
      } transition-all overflow-y-auto overflow-x-hidden absolute bottom-0 z-50 justify-center items-center md:inset-0`}
    >
      <div className="absolute inset-0 backdrop-blur-sm bg-black/30"></div>
      <div className="absolute p-4 w-full max-w-md">
        <div className="absolute w-full top-[14rem] left-[35rem] bg-[#111516] rounded-lg shadow">
          <div className="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
            <h3 className="text-xl font-semibold text-white">New Chat</h3>

            <button
              type="button"
              className="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-[#2E2E2E] dark:hover:text-white"
              data-modal-hide="authentication-modal"
              onClick={() => {
                set_modal(false);
              }}
            >
              <svg
                className="w-3 h-3"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 14 14"
              >
                <path
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
                />
              </svg>
              <span className="sr-only">Close modal</span>
            </button>
          </div>

          <div className="p-4 md:p-5">
            <form className="space-y-4" onSubmit={submit}>
              <div>
                <label
                  for="username"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Add friend
                </label>

                <input
                  type="text"
                  id="username"
                  name="username"
                  autoComplete="off"
                  value={friend_username}
                  onChange={(event) => {
                    set_friend_username(event.target.value);
                  }}
                  placeholder="Enter friend's username"
                  className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-[#2E2E2E] dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
                  required
                />
              </div>

              <button
                type="submit"
                className="w-full text-white bg-[#8b5def] hover:bg-[#764ad3] focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
              >
                Create new chat
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

const Sidebar = ({ user, set_chat }) => {
  const { logout } = useAuth0();
  const [modal, set_modal] = useState(false);
  const [friends, set_friends] = useState([]);
  const [loading, set_loading] = useState(true);

  useEffect(() => {
    const fetchFriends = async () => {
      if (user?.nickname) {
        const response = await fetch_friends(user.nickname);
        if (response.ok) {
          set_loading(false);
          set_friends(await response.json());
        }
      } else {
        set_loading(false);
      }
    };

    fetchFriends();

    return () => {
      set_friends([]);
      set_loading(true);
    };
  }, [user]);

  return (
    user && (
      <>
        <AddFriendModal
          modal={modal}
          set_modal={set_modal}
          username={user.nickname}
          set_friends={set_friends}
        />

        <div className="w-1/4 flex justify-between flex-col h-full">
          <div className="pb-5 pt-4 px-5 border-b-[0.125px] border-gray-600 w-full flex justify-between items-center">
            <span className="text-xl font-semibold text-[#ffffff]">Chats</span>
            <span
              className="text-xl font-semibold text-[#ffffff] bg-[#8b5def] hover:bg-[#764ad3] p-2 cursor-pointer rounded-full"
              onClick={() => {
                set_modal(true);
              }}
            >
              <ChatIcon />
            </span>
          </div>

          <div className="px-4 h-full mt-5 overflow-auto overflow-x-hidden">
            {loading ? (
              <Loader />
            ) : friends.length > 0 ? (
              friends.map((username, index) => (
                <div
                  key={index}
                  onClick={() => {
                    const chat = {
                      chat_id: username.id,
                      username:
                        username.username1 === user.nickname
                          ? username.username2
                          : username.username1,
                    };

                    set_chat(chat);
                  }}
                >
                  <Friends
                    username={
                      username.username1 === user.nickname
                        ? username.username2
                        : username.username1
                    }
                  />
                </div>
              ))
            ) : (
              <div className="flex items-center justify-center gap-5 bg-[#2E2E2E] hover:bg-[#1E1E1E] cursor-pointer p-3 rounded-lg mb-3">
                <span className="text-base font-semibold text-[#FFFFFF]">
                  You haven't connected with anyone yet
                </span>
              </div>
            )}
          </div>

          <div className="py-5 border-t-[0.125px] border-gray-600">
            <div className="px-4 flex justify-between items-center gap-5">
              <div className="flex items-center gap-5">
                <div className="w-9 h-9 overflow-hidden rounded-full">
                  <img
                    alt={user.nickname}
                    className="h-full w-auto"
                    src={fetch_profile_image(user.nickname)}
                  />
                </div>

                <span className="text-base font-semibold text-[#ffffff]">
                  {user.nickname}
                </span>
              </div>

              <span
                className="cursor-pointer"
                onClick={() =>
                  logout({ logoutParams: { returnTo: window.location.origin } })
                }
              >
                <LogoutIcon />
              </span>
            </div>
          </div>
        </div>
      </>
    )
  );
};

export default Sidebar;
