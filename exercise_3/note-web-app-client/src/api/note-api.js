import api from "./api"

export const getAll = async () => {
    return await api.get("notes");
}

export const save = async (payload) => {
    return await api.post("notes", payload)
}

export const update = async (id, payload) => {
    return await api.put("notes/" + id, payload)
}

export const remove = async (id) => {
    return await api.delete("notes/" + id)
}