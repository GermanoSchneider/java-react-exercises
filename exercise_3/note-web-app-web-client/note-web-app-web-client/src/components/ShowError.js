import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { removeError } from "../reducers/error-reducer";

const ShowError = () => {

    const { message } = useSelector(state => state.error);
    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(removeError())
    }, [])

    return (
        <div>
            <p>{message}</p>
        </div>
    )
}

export default ShowError;