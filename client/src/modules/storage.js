import CryptoJS from "crypto-js";

const encrypt = (data) => {
  return CryptoJS.AES.encrypt(
    JSON.stringify(data),
    import.meta.env.VITE_STORAGE_SECRET
  ).toString();
};

const decrypt = (data) => {
  const bytes = CryptoJS.AES.decrypt(data, import.meta.env.VITE_STORAGE_SECRET);
  return JSON.parse(bytes.toString(CryptoJS.enc.Utf8));
};

const store_keys = (
  pre_key,
  signed_pre_key,
  registration_id,
  identity_key_pair
) => {
  const encrypted_pre_key = encrypt(pre_key);
  const encrypted_signed_pre_key = encrypt(signed_pre_key);
  const encrypted_registration_id = encrypt(registration_id);
  const encrypted_identity_key_pair = encrypt(identity_key_pair);

  localStorage.setItem("pre_key", encrypted_pre_key);
  localStorage.setItem("signed_pre_key", encrypted_signed_pre_key);
  localStorage.setItem("registration_id", encrypted_registration_id);
  localStorage.setItem("identity_key_pair", encrypted_identity_key_pair);
};

const retrieve_keys = () => {
  const encrypted_pre_key = localStorage.getItem("pre_key");
  const encrypted_signed_pre_key = localStorage.getItem("signed_pre_key");
  const encrypted_registration_id = localStorage.getItem("registration_id");
  const encrypted_identity_key_pair = localStorage.getItem("identity_key_pair");

  return {
    pre_key: encrypted_pre_key ? decrypt(encrypted_pre_key) : null,
    signed_pre_key: encrypted_signed_pre_key
      ? decrypt(encrypted_signed_pre_key)
      : null,
    registration_id: encrypted_registration_id
      ? decrypt(encrypted_registration_id)
      : null,
    identity_key_pair: encrypted_identity_key_pair
      ? decrypt(encrypted_identity_key_pair)
      : null,
  };
};

export { store_keys, retrieve_keys };
