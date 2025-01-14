import axiosInstance from "./axiosConfig";

export const getCategories = async () => {
  const response = await axiosInstance.get(`list.php?c=list`);
  const { status, data } = response;
  return { status, data };
};
