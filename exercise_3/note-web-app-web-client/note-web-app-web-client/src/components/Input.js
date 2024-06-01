const Input = ({required, type, placeholder, name, value, onChange, onClick}) => {

    return (
        <input 
            placeholder={placeholder} 
            type={type} 
            required={required}
            value={value}
            name={name}
            onChange={onChange}
            onClick={onClick}
        />
    )

}

export default Input;