import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { removeError } from "../reducers/error-reducer";
import Modal from 'react-modal';

Modal.setAppElement('#root');

const ShowError = () => {

    const { message } = useSelector(state => state.error);
    const [modalIsOpen, setModalIsOpen] = useState(false);

    const dispatch = useDispatch()

    useEffect(() => {
        if (message) openModal()
    }, [message])

    const openModal = () => {
        setModalIsOpen(true);
    }

    function closeModal() {
        dispatch(removeError())
        setModalIsOpen(false);
    }

    return (
        <Modal
            className="error-modal"
            isOpen={modalIsOpen}
            onRequestClose={closeModal}
            contentLabel="Error">
            <h2>An error occurred</h2>
            <p>{message}</p>
        </Modal>
    )
}

export default ShowError;