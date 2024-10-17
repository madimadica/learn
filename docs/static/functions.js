const dropdownLinks = document.getElementById("other-links-narrow");

dropdownLinks.addEventListener("click", function () {
  dropdownLinks.classList.toggle("show");
});

function setInputFeedback(inputElement, booleanCorrect) {
  inputElement.classList.remove("is-valid", "is-invalid");
  if (booleanCorrect === true) {
    inputElement.classList.add("is-valid");
  } else if (booleanCorrect === false) {
    inputElement.classList.add("is-invalid");
  }
}

function addSelectOneListener(element) {
  if (!element) {
    console.error(`Element with ID ${selectOneId} not found.`);
    return;
  }

  element.addEventListener("click", function (event) {
    let clickedElement = event.target;
    // Walk up the DOM tree to ensure we're targeting a direct child
    while (clickedElement && clickedElement.parentNode !== element) {
      clickedElement = clickedElement.parentNode;
    }
    if (clickedElement.parentNode === element) {
      Array.from(element.children).forEach((child) => {
        child.classList.remove("correct", "incorrect");
      });
      if (clickedElement.hasAttribute("data-correct")) {
        clickedElement.classList.add("correct");
      } else {
        clickedElement.classList.add("incorrect");
      }
    }
  });
}

function initSelectOneQuestions() {
    const selectOneQuestions = document.querySelectorAll('.quiz .select-one');
    for (const question of selectOneQuestions) {
        addSelectOneListener(question);   
    }
}
