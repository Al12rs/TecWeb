function checkString(input) {
    var wordCount = 0;
    var previousChar = '';
    var ok = true;
    for (var i = 0; i < input.value.length; i++){
        if (input.value.charAt(i) >= 'A' && input.value.charAt(i) <= 'Z') {
            if (previousChar == ' ') {
                wordCount++;
                if (wordCount > 3) {
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
        alert("Noghty...");
    }
}