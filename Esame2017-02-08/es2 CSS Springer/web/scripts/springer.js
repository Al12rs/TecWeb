function checkString(input) {
    var wordCount = 0;
    var previousChar = '';
    var ok = true;
    //Check number of words
    for (var i = 0; i < input.value.length; i++) {
        //check characters
        if (input.value.charAt(i) >= 'a' && input.value.charAt(i) <= 'z') {
            if (previousChar == ' ') {
                wordCount++;
                if (wordCount > 2) {
                    ok = false;
                    break;
                }
            }
        } else if (input.value.charAt(i) != ' ') {
            ok = false;
            break;
        }
        previousChar = input.value.charAt(i);
    }
    if (ok == false) {
        input.value = '';
        alert("Not good string...");
    }
}