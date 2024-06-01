import { getUsername } from "../auth/storage";
import { useEffect, useState } from "react";
import { isNotUndefined, randomId } from "../utils/Utils";
import { getAll, remove, save, update } from "../api/note-api";

const Home = () => {

    const [notes, setNotes] = useState([])
    const [draftNotes, setDraftNotes] = useState([])

    const [selectedNote, setSelectedNote] = useState();

    useEffect(() => {
        getAllNotes()
        .then(notes => setSelectedNote(notes[0]));
    }, [])

    const handleTextChange = (event) => {

        setSelectedNote({
            id: selectedNote?.id,
            text: event.target.value,
            isDraft: selectedNote?.isDraft,
            creation: selectedNote?.creation,
            lastUpdate: selectedNote?.lastUpdate
        })

    }

    const getAllNotes = async () => {
        const response = await getAll()
        setNotes(response.data)
        return response.data;
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

            const response = await save(payload)
            const createdNote = response.data;

            setNotes([...notes, createdNote])
            removeFromDraft(note) 
            setSelectedNote(createdNote)

            await getAllNotes()
        }
    }

    const updateNote = async (note) => {

        if (isNotUndefined(note)) {

            const payload = {
                text: note.text
            }

            const response = await update(note.id, payload)
            const updatedNote = response.data;

            setNotes([...notes, response.data])
            setSelectedNote(updatedNote)

            await getAllNotes()
        }
    }

    const removeNote = async (note) => {

        if (isNotUndefined(note)) {

           if (isDraftNote(note)) { 
                removeFromDraft(note)
           } else {
                await remove(note.id)
                await getAllNotes();
           }

           setSelectedNote(undefined)
        }
    }


    return (
        <div>
            <header>
                <h1>Hello, {getUsername()}</h1>
            </header>
            <button onClick={createDraftNote}>New</button>
            <div>
                <h3>My Draft Notes</h3>
                <ul>
                    {
                        draftNotes.map((note, index) => <li key={index}>
                            <button onClick={() => setSelectedNote(note)}>{note.text}</button>
                        </li>)
                    }
                </ul>
            </div>
            <div>
                <h3>My Saved Notes</h3>
                <ul>
                    {
                        notes.map(note => <li key={note.id}>
                            <a onClick={() => setSelectedNote(note)}>{note.id + ' : ' + note.text}</a>
                        </li>)
                    }
                </ul>
            </div>
            <div>
                <textarea value={selectedNote?.text ?? ''} onChange={handleTextChange}></textarea><br />
                <div>
                    <p>Creation: {selectedNote?.creation}</p>
                    <p>Last update: {selectedNote?.lastUpdate}</p>
                </div>
                {selectedNote?.isDraft ?
                    <button onClick={() => createNote(selectedNote)}>Save</button>
                    : <button onClick={() => updateNote(selectedNote)}>Update</button>
                }
                <button onClick={() => removeNote(selectedNote)}>Delete</button>
            </div>
        </div>
    )
}

export default Home;