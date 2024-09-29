import { useEffect, useState } from "react";
import EmojiPicker from "emoji-picker-react";

const fetch_profile_image = (username) => {
  const identifier = username.slice(0, 1);
  return `https://cdn.auth0.com/avatars/${identifier}.png`;
};

const SendIcon = () => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    className="w-4 h-4"
    viewBox="0 0 24 24"
    fill="white"
    stroke="currentColor"
    strokeWidth="2"
    strokeLinecap="round"
    strokeLinejoin="round"
  >
    <line x1="22" y1="2" x2="11" y2="13" />
    <polygon points="22 2 15 22 11 13 2 9 22 2" />
  </svg>
);

const EmojiIcon = () => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    className="w-4 h-4 text-[#54575c]"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
    strokeLinecap="round"
    strokeLinejoin="round"
  >
    <circle cx="12" cy="12" r="10" />
    <path d="M8 14s1.5 2 4 2 4-2 4-2" />
    <line x1="9" y1="9" x2="9.01" y2="9" />
    <line x1="15" y1="9" x2="15.01" y2="9" />
  </svg>
);

const Message = ({ type, username, messages }) => {
  return (
    <div
      className={`flex ${type === "me" ? "flex-row-reverse" : "flex-row"} mb-5`}
    >
      <div className={`text-center ${type === "me" ? "ml-5 mr-2.5" : "mr-5"}`}>
        <div className="w-9 h-9 overflow-hidden rounded-full">
          <img
            className="h-full w-auto"
            alt={username}
            src={fetch_profile_image(username)}
          />
        </div>
      </div>

      <div className="max-w-[55%] flex flex-col justify-end">
        {messages.map((message, index) => (
          <div
            key={index}
            className={`inline-table mb-2 ${
              type === "me" ? "bg-[#283036]" : "bg-[#8b5def]"
            } text-sm text-[#ffffff] p-3.5 rounded-lg`}
          >
            {message}
          </div>
        ))}
      </div>
    </div>
  );
};

const Loader = () => (
  <div className="flex w-full h-full justify-center items-center bg-[#111516]">
    <div className="loader" />
  </div>
);

const ChatComponent = ({ user, chat }) => {
  const [message, set_message] = useState("");
  const [loading, set_loading] = useState(true);
  const [messages, set_messages] = useState([]);
  const [is_emoji_picker_open, set_is_emoji_picker_open] = useState(false);

  const on_emoji_click = (emoji_object) => {
    set_message((prev_message) => prev_message + emoji_object.emoji);
  };

  const submit = async () => {
    const headers = new Headers();
    headers.append("Content-Type", "application/json");

    const request_options = {
      method: "POST",
      headers: headers,
      body: JSON.stringify({
        content: message,
      }),
    };

    const response = await fetch(
      `${import.meta.env.VITE_SERVER_BASE_URL}/api/Message/send/chat/${
        chat.chat_id
      }/user/${user.nickname}`,
      request_options
    );

    const result = await response.json();

    if (response.status === 404) {
      alert(result.message[0]);
    } else {
      set_messages((prev_messages) => [...prev_messages, result]);
      set_message("");
    }
  };

  useEffect(() => {
    const fetchMessages = async () => {
      const response = await fetch(
        `${import.meta.env.VITE_SERVER_BASE_URL}/api/Chat/getMessages/${
          chat.chat_id
        }/user/${user.nickname}`
      );

      const result = await response.json();

      if (response.status === 404) {
        alert(result.message[0]);
      } else {
        set_messages(result);
        set_loading(false);
      }
    };

    fetchMessages();
    const interval = setInterval(fetchMessages, 100);

    return () => {
      set_messages([]);
      set_loading(true);
      clearInterval(interval);
    };
  }, [chat.chat_id, user.nickname]);

  return loading ? (
    <Loader />
  ) : (
    <>
      <div className="absolute bottom-[14%] px-8">
        <EmojiPicker
          open={is_emoji_picker_open}
          onEmojiClick={on_emoji_click}
        />
      </div>

      <div className="py-5 border-b-[0.125px] border-gray-600">
        <div className="px-8 flex items-center gap-5">
          <div className="w-9 h-9 overflow-hidden rounded-full">
            <img
              alt={chat.username}
              className="h-full w-auto"
              src={fetch_profile_image(chat.username)}
            />
          </div>

          <span className="text-base font-semibold text-[#c0b8b8]">
            {chat.username}
          </span>
        </div>
      </div>

      <div className="px-8">
        <div className="py-4 overflow-auto h-[calc(100vh_-_55px_-_2em_-_25px_*_2_-_.5em_-_3em)]">
          {messages.map((message, index) => {
            return (
              <Message
                key={index}
                messages={[message.content]}
                username={message.appUser.username}
                type={message.appUser.username === user.nickname ? "me" : ""}
              />
            );
          })}
        </div>

        <div className="bg-[#131719] rounded-lg p-3 mt-10">
          <div className="flex flex-row items-center space-x-4">
            <button
              className="bg-transparent rounded-full p-2.5 flex items-center justify-center"
              onClick={() => set_is_emoji_picker_open(!is_emoji_picker_open)}
            >
              <EmojiIcon />
            </button>

            <input
              type="text"
              value={message}
              onChange={(event) => {
                set_message(event.target.value);
              }}
              placeholder="Type a message..."
              className="w-full bg-transparent border-0 text-sm text-[#a3a3a3] placeholder-[#a3a3a3] focus:outline-none"
            />

            <button
              onClick={submit}
              className="bg-[#8b5def] hover:bg-[#764ad3] text-white p-2.5 rounded-full flex items-center justify-center"
            >
              <SendIcon />
            </button>
          </div>
        </div>
      </div>
    </>
  );
};

const Chat = ({ user, chat }) => {
  return (
    <div className="bg-opacity-95 bg-[#0a0e0e] w-3/4 h-full relative overflow-hidden">
      {chat.chat_id === 0 ? (
        <div className="flex flex-col w-full h-screen justify-center items-center gap-8">
          <img
            alt="Ethos Logo"
            src="https://assets.devfolio.co/hackathons/c319fcf0bd204d3d9acc419c27e5dcb0/assets/favicon/275.png"
          />

          <span className="text-white font-semibold text-3xl">Ethos IITG</span>
        </div>
      ) : (
        <ChatComponent user={user} chat={chat} />
      )}
    </div>
  );
};

export default Chat;
