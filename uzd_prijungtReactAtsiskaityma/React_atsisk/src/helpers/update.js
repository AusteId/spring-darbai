import axios from "axios";
const url = "http://localhost:8080/api/books";

export const updateData = async (id, data) => {
    const response = await axios.patch(`${url}/${id}`, data);
    return response.data; 
}; 