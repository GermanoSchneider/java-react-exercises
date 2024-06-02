import { getUsername, removeToken } from "../auth/storage";
import { useEffect, useState } from "react";
import { isNotUndefined, randomId } from "../utils/Utils";
import { getAll, remove, save, update } from "../api/note-api";
import { useNavigate } from "react-router-dom";
import ShowError from "../components/ShowError";
import { useDispatch } from "react-redux";
import { addError } from "../reducers/error-reducer";

const Home = () => {

    const [notes, setNotes] = useState([])
    const [draftNotes, setDraftNotes] = useState([])

    const [selectedNote, setSelectedNote] = useState();

    const navigate = useNavigate();

    const dispatch = useDispatch()

    useEffect(() => {
        getAllNotes()
            .then(notes => setSelectedNote(notes[0]));
    }, [])

    const handleTextChange = (event) => {

        if (isNotUndefined(selectedNote)) {
            setSelectedNote({
                id: selectedNote?.id,
                text: event.target.value,
                isDraft: selectedNote?.isDraft,
                creation: selectedNote?.creation,
                lastUpdate: selectedNote?.lastUpdate
            })
        }

    }

    const getAllNotes = async () => {
       return await getAll()
            .then((response) => {
                setNotes(response.data)
                return response.data;
            }).catch(error => dispatch(addError(error.message)))
    }

    const createDraftNote = () => {

        const draftNote = {
            id: randomId(),
            text: 'My draft note',
            isDraft: true
        }

        setDraftNotes([...draftNotes, draftNote])
        setSelectedNote(draftNote)
    }

    const isDraftNote = (note) => {
        return note?.isDraft;
    }

    const removeFromDraft = (note) => {
        setDraftNotes(draftNote => draftNote.filter(item => item.id !== note.id))
    }

    const createNote = async (note) => {

        if (isNotUndefined(note)) {

            const payload = {
                text: note.text
            }

            await save(payload)
                .then(async (response) => {

                    const createdNote = response.data;

                    setNotes([...notes, createdNote])
                    removeFromDraft(note)
                    setSelectedNote(createdNote)

                    await getAllNotes()
                })
                .catch(error => dispatch(addError(error.message)))
        }
    }

    const updateNote = async (note) => {

        if (isNotUndefined(note)) {

            const payload = {
                text: note.text
            }

            await update(note.id, payload)
                .then(async (response) => {

                    const updatedNote = response.data;

                    setNotes([...notes, response.data])
                    setSelectedNote(updatedNote)

                    console.log("CAIU AQUI")

                    await getAllNotes()

                })
                .catch(error => {
                    console.log(error)
                    dispatch(addError(error.message))
                })


        }
    }

    const removeNote = async (note) => {

        if (isNotUndefined(note)) {

            if (isDraftNote(note)) {
                removeFromDraft(note)
                setSelectedNote(undefined)
            } else {
                await remove(note.id)
                    .then(async () => {
                        await getAllNotes();
                        setSelectedNote(undefined)
                    }).catch(error => dispatch(addError(error.message)))

            }


        }
    }

    const logout = () => {
        removeToken()
        navigate("/login")
    }


    return (
        <div>
            <ShowError />
            <div className="container">
                <div className="sidebar">
                    <button className="sidebar-btn-new" onClick={createDraftNote}>New</button>
                    <h3>My notes</h3>
                    {draftNotes.length > 0 || notes.length > 0 ?
                        <div>
                            {draftNotes.map((note, index) =>
                                <button key={index} className="sidebar-btn-note sidebar-draft-note" onClick={() => setSelectedNote(note)}>
                                    {note.text.substring(0, 20)}
                                </button>)}
                            {notes.map((note, index) =>
                                <button key={index} className="sidebar-btn-note" onClick={() => setSelectedNote(note)}>
                                    {note.text.substring(0, 20)}
                                </button>)}
                        </div>
                        : <p>There are no notes</p>}

                </div>
                <div className="notepad">
                    <header>
                        <div className="logout">
                            <p>{getUsername()} | <button onClick={logout}>Logout</button></p>
                        </div>
                    </header>
                    <textarea onChange={handleTextChange} value={selectedNote?.text}></textarea>
                    {isNotUndefined(selectedNote)
                        ?
                        <div>
                            <div className="notepad-note-info">
                                <p>Creation: {selectedNote?.creation?.split(".")[0]}</p>
                                <p>Last update: {selectedNote?.creation?.split(".")[0]}</p>
                            </div>
                            <div className="notepad-btn-actions">
                                {selectedNote?.isDraft ?
                                    <button onClick={() => createNote(selectedNote)}>Save</button>
                                    : <button onClick={() => updateNote(selectedNote)}>Update</button>
                                }
                                <button onClick={() => removeNote(selectedNote)}>Delete</button>
                            </div>
                        </div> : <div></div>}
                </div>
            </div>
        </div>

    )
}

export default Home;