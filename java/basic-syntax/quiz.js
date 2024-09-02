

const Quiz = {
    _checkExact(number, solutions) {
        return this._check(number, solutions, true);
    },
    _checkInsensitive(number, solutions) {
        return this._check(number, solutions, false);
    },
    _check: function(number, solutions, caseSensitive) {
        const input = document.getElementById("q" + number + "-input");
        const inputValue = caseSensitive
                ? input.value.trim()
                : input.value.trim().toLowerCase();
        let correct = false;
        for (var s of solutions) {
            if (s === inputValue) {
                correct = true;
                break;
            }
        }
        setInputFeedback(input, correct);
    },
    check1: function() {
        this._checkExact(1, ["Hello.java"]);
    },
    check2: function() {
        this._checkInsensitive(2, [";", "semicolon"]);
    },
    check3: function() {
        this._checkExact(3, ["main", "main method", "the main method", "main(String[] args)"]);
    },
    check4: function() {
        this._checkInsensitive(4, ["0", "zero"]);
    },
    check15: function() {
        this._checkExact(15, ["5"]);
    }
};

initSelectOneQuestions();

