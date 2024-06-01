export const randomId = function(length = 6) {
    return Math.random().toString(36).substring(2, length+2);
};

export const isNotUndefined = function(value) {
    return value !== undefined;
}