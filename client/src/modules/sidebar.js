const fetch_friends = async (username) => {
  const request_options = {
    method: "GET",
    redirect: "follow",
  };

  const response = await fetch(
    `${import.meta.env.VITE_SERVER_BASE_URL}/api/Chat/Friends/user/${username}`,
    request_options
  );

  return response;
};

export { fetch_friends };
