import { AsyncStorage } from "react-native";

export const setItemStorage = (key, value) => {
  return AsyncStorage.setItem(key, value)
    .then(() => {
      console.log("Armazenado em AsyncStorage");
    })
    .catch(err => console.log("Não é possível guardar no AsyncStorage"));
};

export const getItemStorage = key => {
  return AsyncStorage.getItem(key)
    .then(data => {
      return data;
    })
    .catch(err => console.log("Fallo"));
};

export const removeItemStorage = key => {
  return AsyncStorage.removeItem(key)
    .then(() => {
      console.log("Item removido do AsyncStorage");
    })
    .catch(err => console.log("Não é possível remover o item do AsyncStorage"));
};
