import axios from "axios";
const url = "http://localhost:8080/api/books";

export const postdata = async (data) => {

    let response = await axios.post(url, data);
    return response.data;

};

