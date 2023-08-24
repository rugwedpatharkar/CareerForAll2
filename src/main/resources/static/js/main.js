
(function() {
  "use strict";

  /**
   * Easy selector helper function
   */
  const select = (el, all = false) => {
    el = el.trim()
    if (all) {
      return [...document.querySelectorAll(el)]
    } else {
      return document.querySelector(el)
    }
  }

  /**
   * Easy event listener function
   */
  const on = (type, el, listener, all = false) => {
    if (all) {
      select(el, all).forEach(e => e.addEventListener(type, listener))
    } else {
      select(el, all).addEventListener(type, listener)
    }
  }

  /**
   * Easy on scroll event listener 
   */
  const onscroll = (el, listener) => {
    el.addEventListener('scroll', listener)
  }

  /**
   * Sidebar toggle
   */
  if (select('.toggle-sidebar-btn')) {
    on('click', '.toggle-sidebar-btn', function(e) {
      select('body').classList.toggle('toggle-sidebar')
    })
  }

  /**
   * Search bar toggle
   */
  if (select('.search-bar-toggle')) {
    on('click', '.search-bar-toggle', function(e) {
      select('.search-bar').classList.toggle('search-bar-show')
    })
  }

  /**
   * Navbar links active state on scroll
   */
  let navbarlinks = select('#navbar .scrollto', true)
  const navbarlinksActive = () => {
    let position = window.scrollY + 200
    navbarlinks.forEach(navbarlink => {
      if (!navbarlink.hash) return
      let section = select(navbarlink.hash)
      if (!section) return
      if (position >= section.offsetTop && position <= (section.offsetTop + section.offsetHeight)) {
        navbarlink.classList.add('active')
      } else {
        navbarlink.classList.remove('active')
      }
    })
  }
  window.addEventListener('load', navbarlinksActive)
  onscroll(document, navbarlinksActive)

  /**
   * Toggle .header-scrolled class to #header when page is scrolled
   */
  let selectHeader = select('#header')
  if (selectHeader) {
    const headerScrolled = () => {
      if (window.scrollY > 100) {
        selectHeader.classList.add('header-scrolled')
      } else {
        selectHeader.classList.remove('header-scrolled')
      }
    }
    window.addEventListener('load', headerScrolled)
    onscroll(document, headerScrolled)
  }

  /**
   * Back to top button
   */
  let backtotop = select('.back-to-top')
  if (backtotop) {
    const toggleBacktotop = () => {
      if (window.scrollY > 100) {
        backtotop.classList.add('active')
      } else {
        backtotop.classList.remove('active')
      }
    }
    window.addEventListener('load', toggleBacktotop)
    onscroll(document, toggleBacktotop)
  }

  /**
   * Initiate tooltips
   */
  var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
  var tooltipList = tooltipTriggerList.map(function(tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl)
  })

  /**
   * Initiate quill editors
   */
  if (select('.quill-editor-default')) {
    new Quill('.quill-editor-default', {
      theme: 'snow'
    });
  }

  if (select('.quill-editor-bubble')) {
    new Quill('.quill-editor-bubble', {
      theme: 'bubble'
    });
  }

  if (select('.quill-editor-full')) {
    new Quill(".quill-editor-full", {
      modules: {
        toolbar: [
          [{
            font: []
          }, {
            size: []
          }],
          ["bold", "italic", "underline", "strike"],
          [{
              color: []
            },
            {
              background: []
            }
          ],
          [{
              script: "super"
            },
            {
              script: "sub"
            }
          ],
          [{
              list: "ordered"
            },
            {
              list: "bullet"
            },
            {
              indent: "-1"
            },
            {
              indent: "+1"
            }
          ],
          ["direction", {
            align: []
          }],
          ["link", "image", "video"],
          ["clean"]
        ]
      },
      theme: "snow"
    });
  }

  /**
   * Initiate TinyMCE Editor
   */
  const useDarkMode = window.matchMedia('(prefers-color-scheme: dark)').matches;
  const isSmallScreen = window.matchMedia('(max-width: 1023.5px)').matches;

  tinymce.init({
    selector: 'textarea.tinymce-editor',
    plugins: 'preview importcss searchreplace autolink autosave save directionality code visualblocks visualchars fullscreen image link media template codesample table charmap pagebreak nonbreaking anchor insertdatetime advlist lists wordcount help charmap quickbars emoticons',
    editimage_cors_hosts: ['picsum.photos'],
    menubar: 'file edit view insert format tools table help',
    toolbar: 'undo redo | bold italic underline strikethrough | fontfamily fontsize blocks | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist | forecolor backcolor removeformat | pagebreak | charmap emoticons | fullscreen  preview save print | insertfile image media template link anchor codesample | ltr rtl',
    toolbar_sticky: true,
    toolbar_sticky_offset: isSmallScreen ? 102 : 108,
    autosave_ask_before_unload: true,
    autosave_interval: '30s',
    autosave_prefix: '{path}{query}-{id}-',
    autosave_restore_when_empty: false,
    autosave_retention: '2m',
    image_advtab: true,
    link_list: [{
        title: 'My page 1',
        value: 'https://www.tiny.cloud'
      },
      {
        title: 'My page 2',
        value: 'http://www.moxiecode.com'
      }
    ],
    image_list: [{
        title: 'My page 1',
        value: 'https://www.tiny.cloud'
      },
      {
        title: 'My page 2',
        value: 'http://www.moxiecode.com'
      }
    ],
    image_class_list: [{
        title: 'None',
        value: ''
      },
      {
        title: 'Some class',
        value: 'class-name'
      }
    ],
    importcss_append: true,
    file_picker_callback: (callback, value, meta) => {
      /* Provide file and text for the link dialog */
      if (meta.filetype === 'file') {
        callback('https://www.google.com/logos/google.jpg', {
          text: 'My text'
        });
      }

      /* Provide image and alt text for the image dialog */
      if (meta.filetype === 'image') {
        callback('https://www.google.com/logos/google.jpg', {
          alt: 'My alt text'
        });
      }

      /* Provide alternative source and posted for the media dialog */
      if (meta.filetype === 'media') {
        callback('movie.mp4', {
          source2: 'alt.ogg',
          poster: 'https://www.google.com/logos/google.jpg'
        });
      }
    },
    templates: [{
        title: 'New Table',
        description: 'creates a new table',
        content: '<div class="mceTmpl"><table width="98%%"  border="0" cellspacing="0" cellpadding="0"><tr><th scope="col"> </th><th scope="col"> </th></tr><tr><td> </td><td> </td></tr></table></div>'
      },
      {
        title: 'Starting my story',
        description: 'A cure for writers block',
        content: 'Once upon a time...'
      },
      {
        title: 'New list with dates',
        description: 'New List with dates',
        content: '<div class="mceTmpl"><span class="cdate">cdate</span><br><span class="mdate">mdate</span><h2>My List</h2><ul><li></li><li></li></ul></div>'
      }
    ],
    template_cdate_format: '[Date Created (CDATE): %m/%d/%Y : %H:%M:%S]',
    template_mdate_format: '[Date Modified (MDATE): %m/%d/%Y : %H:%M:%S]',
    height: 600,
    image_caption: true,
    quickbars_selection_toolbar: 'bold italic | quicklink h2 h3 blockquote quickimage quicktable',
    noneditable_class: 'mceNonEditable',
    toolbar_mode: 'sliding',
    contextmenu: 'link image table',
    skin: useDarkMode ? 'oxide-dark' : 'oxide',
    content_css: useDarkMode ? 'dark' : 'default',
    content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:16px }'
  });

  /**
   * Initiate Bootstrap validation check
   */
  var needsValidation = document.querySelectorAll('.needs-validation')

  Array.prototype.slice.call(needsValidation)
    .forEach(function(form) {
      form.addEventListener('submit', function(event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }

        form.classList.add('was-validated')
      }, false)
    })

  /**
   * Initiate Datatables
   */
  const datatables = select('.datatable', true)
  datatables.forEach(datatable => {
    new simpleDatatables.DataTable(datatable);
  })

  /**
   * Autoresize echart charts
   */
  const mainContainer = select('#main');
  if (mainContainer) {
    setTimeout(() => {
      new ResizeObserver(function() {
        select('.echart', true).forEach(getEchart => {
          echarts.getInstanceByDom(getEchart).resize();
        })
      }).observe(mainContainer);
    }, 200);
  }

})();

//--------------------

var emailInput1 = document.getElementById("email");
if(emailInput1 !== null){
	// Add an event listener to the email input field
emailInput1.addEventListener("input", validateEmail);

function validateEmail() {
  var email = document.getElementById("email").value;
  var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Basic email regex
  var emailError = document.getElementById("emailError");
  if (!emailRegex.test(email)) {
    emailError.style.display = "block";
    return false;
  } else {
    emailError.style.display = "none";
    return true;
  }
}

}

//---------------

 // Wait for the DOM to be fully loaded
        document.addEventListener("DOMContentLoaded", function() {
            // Get the logout message element
            const logoutMessage = document.querySelector(".logoutMessage");

            // If the logout message element exists, hide it after 2 seconds
            if (logoutMessage) {
                setTimeout(function() {
                    logoutMessage.style.display = "none";
                }, 2000);
            }
        });

//---------------------


//----------------------------------------------------------Startup Onboarding & Startup List JS-------------------------------------
//country state city dropdowns-----------------------------------------------
// Function to get the access token from site for API request
/*async function getAccessToken() {
  const response = await fetch('https://www.universal-tutorial.com/api/getaccesstoken', {
    headers: {
      'Accept': 'application/json',
      'api-token': 'umiCzZgdo_PBAgBq33MRiXwJmpqGzWUrDLpaYvaOz1Q_z5gb4zzoIRjd3zWawdTTuO0',
      'user-email': 'techentrepreneurs13@gmail.com'
    }
  });
  const data = await response.json();
  return data.auth_token;
}*/
var res = 0;
async function getAccessToken() {
  try {
    const response = await fetch('https://www.universal-tutorial.com/api/getaccesstoken', {
      headers: {
        'Accept': 'application/json',
        'api-token': 'umiCzZgdo_PBAgBq33MRiXwJmpqGzWUrDLpaYvaOz1Q_z5gb4zzoIRjd3zWawdTTuO0',
        'user-email': 'techentrepreneurs13@gmail.com'
      }
    });

    if (!response.ok) {
      // The request was not successful, handle the error here
      throw new Error('Failed to fetch access token.');
    }

    const data = await response.json();
    res = 1;
    return data.auth_token;
  } catch (error) {
    // Handle any other errors that may occur during the fetch process
    console.error('Error occurred while fetching access token:', error);
    return null; // or throw the error further to handle it at a higher level
  }
}

// Function to populate the countries dropdown
async function populateCountriesDropdown() {
  const tok = await getAccessToken();  
  fetch('https://www.universal-tutorial.com/api/countries/', {
    headers: {
      'Authorization': `Bearer ${tok}`,
      'Accept': 'application/json'
    }
  })
  .then(response => response.json())
  .then(data => {
    const countryDropdown = document.getElementById('country-dropdown');
    if(countryDropdown !== null){
		countryDropdown.innerHTML = '<option value="" disabled selected>-- Please select country --</option>';
    data.forEach(country => {
      const option = document.createElement('option');
      option.value = country.country_name;
      option.textContent = country.country_name;
      countryDropdown.appendChild(option);
    });
	}
  })
  .catch(error => console.error(error));
}

// Function to populate the states dropdown based on the selected country
async function populateStatesDropdown() {
  const tok = await getAccessToken(); 
  const countryDropdown = document.getElementById('country-dropdown');
  const stateDropdown = document.getElementById('state-Dropdown');
  if(countryDropdown !== null){
	  countryDropdown.addEventListener('change', async () => {
    const countryName = countryDropdown.value;
    const response = await fetch(`https://www.universal-tutorial.com/api/states/${countryName}`, {
      headers: {
        'Authorization': `Bearer ${tok}`,
        'Accept': 'application/json'
      }
    });
    const data = await response.json();
    stateDropdown.innerHTML = '<option value="" disabled selected>-- Please select state --</option>';
    data.forEach(state => {
      const option = document.createElement('option');
      option.value = state.state_name;
      option.textContent = state.state_name;
      stateDropdown.appendChild(option);
    });
  });
  }
}

// Function to populate cities based on the selected state
async function populateCitiesDropdown() {
  const tok = await getAccessToken(); 
  const stateDropdown = document.getElementById("state-Dropdown");
  const cityDropdown = document.getElementById("city-Dropdown");
  if(stateDropdown !== null){
	  stateDropdown.addEventListener("change", async (event) => {
    const selectedState = event.target.value;
    const response = await fetch(`https://www.universal-tutorial.com/api/cities/${selectedState}`, {
      headers: {
        'Authorization': `Bearer ${tok}`,
        "Accept": "application/json"
      }
    }); 
    const cities = await response.json();
    cityDropdown.innerHTML = '<option value="" disabled selected>-- Please select city --</option>';
    cities.forEach(city => {
      const option = document.createElement('option');
      option.value = city.city_name;
      option.text = city.city_name;
      cityDropdown.add(option);
    });
  });
  } 
}
// Call the functions to populate the dropdowns
populateCountriesDropdown();
populateStatesDropdown();
populateCitiesDropdown();
//end of country state city dropdowns-----------------------------------------------
//------------------company email validation----------------
var emailInput = document.getElementById("companyEmail");
if(emailInput !== null){
	// Add an event listener to the email input field
emailInput.addEventListener("input", validateCompanyEmail);

function validateCompanyEmail() {
  var email = document.getElementById("companyEmail").value;
  var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Basic email regex
  var companyEmailError = document.getElementById("companyEmailError");
  if (email === "") {
    companyEmailError.style.display = "none"; // Hide the error message if the field is empty
    return true;
  } else if (!emailRegex.test(email)) {
    companyEmailError.style.display = "block";
    return false;
  } else {
    companyEmailError.style.display = "none";
    return true;
  }
}
}
//------------------end of company email validation----------------
//validation of presentation file--------------------
var presentation = document.getElementById('presentation');
if(presentation !== null){
	presentation.addEventListener('change', validateFile);
var presentationSizeError = document.getElementById("presentationSizeError");
var presentationTypeError = document.getElementById("presentationTypeError");
function validateFile() {
    const fileInput = document.getElementById('presentation');
    const file = fileInput.files[0];
    const fileSize = file.size;
    const maxSize = 2097152; // 2 MB in bytes
    const allowedTypes = ['pdf', 'ppt', 'pptx', 'doc'];
    const fileType = file.name.split('.').pop();

    if (fileSize > maxSize) {
        presentationSizeError.style.display = "block";
        fileInput.value = ''; // Reset the file input element
        return false;
    }

    if (!allowedTypes.includes(fileType)) {
        presentationTypeError.style.display = "block";
        fileInput.value = ''; // Reset the file input element
        return false;
    }
	presentationSizeError.style.display = "none";
	presentationTypeError.style.display = "none";
    return true;
}
}
//end of validation of presentation file--------------------

//other file validation--------------------------
var otherfile = document.getElementById('otherfile');
if(otherfile !== null){
	otherfile.addEventListener('change', validateOtherFile);
var otherfileSizeError = document.getElementById("otherfileSizeError");
var otherfileTypeError = document.getElementById("otherfileTypeError");
function validateOtherFile() {
    const fileInput = document.getElementById('otherfile');
    const file = fileInput.files[0];
    const fileSize = file.size;
    const maxSize = 2097152; // 2 MB in bytes
    const allowedTypes = ['pdf', 'ppt', 'pptx', 'doc'];
    const fileType = file.name.split('.').pop();

    if (fileSize > maxSize) {
        otherfileSizeError.style.display = "block";
        fileInput.value = ''; // Reset the file input element
        return false;
    }

    if (!allowedTypes.includes(fileType)) {
        otherfileTypeError.style.display = "block";
        fileInput.value = ''; // Reset the file input element
        return false;
    }
	otherfileSizeError.style.display = "none";
	otherfileTypeError.style.display = "none";
    return true;
}
}
//end of other file validation----------------------
//auto increment companyId--------
 var nameInput = document.getElementById("companyId");
 if(nameInput !== null){
	 nameInput.addEventListener("input", incrementInput);
	function incrementInput() {
		console.log("incrementInput called");
  var input = document.getElementById("companyId");
  input.value++;
}
 }
//end of autoincrement companyId-------

//---------------validate Company name (with numbers)---------------
var companynameInput = document.getElementById("companyName");
if (companynameInput !== null) {
  // Add an event listener to the name input field
  companynameInput.addEventListener("input", validateCompanyName);

  function validateCompanyName() {
    var name = companynameInput.value;
    var nameRegex = /^(?=.*[a-zA-Z])[a-zA-Z0-9]+(([',. -][a-zA-Z ])?[a-zA-Z0-9]*)*$/;
    var companyNameError = document.getElementById("companyNameError");
    
    if (name.trim() === '') {
      companyNameError.style.display = "none"; // Hide error when field is empty
      return true; // No error when field is empty
    }
    
    if (!nameRegex.test(name)) {
      companyNameError.style.display = "block";
      return false;
    } else {
      companyNameError.style.display = "none";
      return true;
    }
  }
}
//end of validate company name--------

//validate Brand name (with numbers)-------
var nameInput = document.getElementById("brandName");
if(nameInput !== null){
	// Add an event listener to the name input field
nameInput.addEventListener("input", validateBrandName);

function validateBrandName() {
	//console.log("validate brandname called");
  var name = document.getElementById("brandName").value;
  var nameRegex = /^(?=.*[a-zA-Z])[a-zA-Z0-9]+(([',. -][a-zA-Z ])?[a-zA-Z0-9]*)*$/;
  var brandNameError = document.getElementById("brandNameError");
  if (name.trim() === '') {
      brandNameError.style.display = "none"; // Hide error when field is empty
      return true; // No error when field is empty
    }
  if (!nameRegex.test(name)) {
    brandNameError.style.display = "block";
    return false;
  } else {
	brandNameError.style.display = "none";
    return true;
  }
}
}
//end of validate Brand name--------

//founder name validation (without numbers)---------------
var foundernameInput = document.getElementById("founderName");
if(foundernameInput !== null){
	// Add an event listener to the name input field
foundernameInput.addEventListener("input", validateFounderName);

function validateFounderName() {
  var name = document.getElementById("founderName").value;
  var nameRegex = /^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$/;
  var founderNameError = document.getElementById("founderNameError");
  if (name.trim() === '') {
      founderNameError.style.display = "none"; // Hide error when field is empty
      return true; // No error when field is empty
    }
  if (!nameRegex.test(name)) {
    founderNameError.style.display = "block";
    return false;
  } else {
	  founderNameError.style.display = "none";
    return true;
  }
}
}
//end of founder name valdation----------------

//ceo/md name validation (without numbers)---------------
var nameInput = document.getElementById("ceoName");
if(nameInput !== null){
	// Add an event listener to the name input field
nameInput.addEventListener("input", validateCeoName);

function validateCeoName() {
  var name = document.getElementById("ceoName").value;
  var nameRegex = /^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$/;
  var ceoNameError = document.getElementById("ceoNameError");
  if (name.trim() === '') {
      ceoNameError.style.display = "none"; // Hide error when field is empty
      return true; // No error when field is empty
    }
  if (!nameRegex.test(name)) {
    ceoNameError.style.display = "block";
    return false;
  } else {
	ceoNameError.style.display = "none";
    return true;
  }
}
}

//end of ceo/md name valdation----------------

//company website validation------------
var websiteInput = document.getElementById("companyWebsite");
if(websiteInput !== null){
	// Add an event listener to the company website input field
websiteInput.addEventListener("blur", validateWebsite);

function validateWebsite() {
  var website = document.getElementById("companyWebsite").value.trim();
  var websiteRegex = /^(ftp|http|https):\/\/[^ "]+$/;
  var companyWebsiteError = document.getElementById("companyWebsiteError");
  if (website === "") {
    companyWebsiteError.style.display = "none"; // Hide the error message if the field is empty
    return true;
  } else if (!websiteRegex.test(website)) {
    companyWebsiteError.style.display = "block";
    return false;
  } else {
	companyWebsiteError.style.display = "none";
    return true;
  }
}
}
// end of company website validation--------------

// Company LinkedIn validation
var linkedinInput = document.getElementById("companyLinkedin");
if(linkedinInput !== null){
	linkedinInput.addEventListener("blur", validateLinkedin);

function validateLinkedin() {
  var linkedin = document.getElementById("companyLinkedin").value.trim(); // Trim the value to remove leading/trailing spaces
  var linkedinRegex = /^(http(s)?:\/\/)?([\w]+\.)?linkedin\.com\/company\/[\w-]+(\/.*)?$/;
  var companyLinkedinError = document.getElementById("companyLinkedinError");

  if (linkedin === "") {
    companyLinkedinError.style.display = "none"; // Hide the error message if the field is empty
    return true;
  } else if (!linkedinRegex.test(linkedin)) {
    companyLinkedinError.style.display = "block";
    return false;
  } else {
    companyLinkedinError.style.display = "none";
    return true;
  }
}
}


// Other link validation
var relevantLinkInput = document.getElementById("relevantLink");
if(relevantLinkInput !== null){
	relevantLinkInput.addEventListener("blur", validateRelevantLink);

function validateRelevantLink() {
  var relevantLink = document.getElementById("relevantLink").value.trim(); // Trim the value to remove leading/trailing spaces
  var urlRegex = /^(http(s)?:\/\/)?([\w-]+\.)+[\w-]+(\/[\w- ;,./?%&=]*)?$/i;
  var relevantLinkError = document.getElementById("relevantLinkError");

  if (relevantLink === "") {
    relevantLinkError.style.display = "none"; // Hide the error message if the field is empty
    return true;
  } else if (!urlRegex.test(relevantLink)) {
    relevantLinkError.style.display = "block";
    return false;
  } else {
    relevantLinkError.style.display = "none";
    return true;
  }
}
}

//future date validation---------------
 // Get the current date in the format yyyy-mm-dd
  const currentDate = new Date().toISOString().split('T')[0];
  // Set the max attribute of the input element to the current date
  var startdate = document.querySelector('#startDate');
  if(startdate !== null){
	  console.log("sdate");
	startdate.setAttribute('max', currentDate);  
  }
//end of future date validation----------

//---------------validate Competitor (with numbers)---------------
var competitorInput = document.getElementById("competitor");
if (competitorInput !== null) {
  // Add an event listener to the name input field
  competitorInput.addEventListener("input", validateCompetitorName);

  function validateCompetitorName() {
    var name = competitorInput.value;
    var nameRegex = /^(?=.*[a-zA-Z])[a-zA-Z0-9]+(([',. -][a-zA-Z ])?[a-zA-Z0-9]*)*$/;
    var competitorNameError = document.getElementById("competitorNameError");
    
    if (name.trim() === '') {
      competitorNameError.style.display = "none"; // Hide error when field is empty
      return true; // No error when field is empty
    }
    
    if (!nameRegex.test(name)) {
      competitorNameError.style.display = "block";
      return false;
    } else {
      competitorNameError.style.display = "none";
      return true;
    }
  }
}
//end of validate Competitor name--------

//reset startup onboarding form-----------
var reset = document.getElementById("resetForm");
if(reset !== null){
	// Add an event listener to the relevant link input field
reset.addEventListener("blur", resetForm);
function resetForm() {
  document.getElementById("onboarding").reset();
}
}
//end of startup onboarding reset form---------------

//reset startup update form-----------
var reset = document.getElementById("resetupdateform");
if(reset !== null){
	// Add an event listener to the relevant link input field
reset.addEventListener("blur", resetupdateForm);
function resetupdateForm() {
  document.getElementById("updatestartup").reset();
}
}
//end of startup update reset form---------------

//Validate startup onboarding submission------------------
const form = document.getElementById('onboarding'); // replace 'myForm' with the ID of your form element 
if(form !== null){
	form.addEventListener('submit', function(event) {
  const presentationValid = validateFile();
  if (!presentationValid) {
    event.preventDefault();
    return;
  }
  const otherFileValid = validateOtherFile();
  if (!otherFileValid) {
    event.preventDefault();
    return;
  }
  const CompanyNameValid = validateCompanyName();
  if (!CompanyNameValid) {
    event.preventDefault();
    return;
  }
  const CompetitorNameValid = validateCompetitorName();
  if (!CompetitorNameValid) {
    event.preventDefault();
    return;
  }
  const BrandNameValid = validateBrandName();
  if (!BrandNameValid) {
    event.preventDefault();
    return;
  }
  const FounderNameValid = validateFounderName();
  if (!FounderNameValid) {
    event.preventDefault();
    return;
  }
  const CeoNameValid = validateCeoName();
  if (!CeoNameValid) {
    event.preventDefault();
    return;
  }
  const WebsiteValid = validateWebsite();
  if (!WebsiteValid) {
    event.preventDefault();
    return;
  }
  const LinkedinValid = validateLinkedin();
  if (!LinkedinValid) {
    event.preventDefault();
    return;
  }
  const RelevantLinkValid = validateRelevantLink();
  if (!RelevantLinkValid) {
    event.preventDefault();
    return;
  }
});
}

//End of startup onboarding validate submission----------------
//Validate startup update submission------------------
const startupUpdateform = document.getElementById('updatestartup'); // replace 'myForm' with the ID of your form element 
if(startupUpdateform !== null){
	startupUpdateform.addEventListener('submit', function(event) {
  const presentationValid = validateFile();
  if (!presentationValid) {
    event.preventDefault();
    return;
  }
  const otherFileValid = validateOtherFile();
  if (!otherFileValid) {
    event.preventDefault();
    return;
  }
  const CompanyNameValid = validateCompanyName();
  if (!CompanyNameValid) {
    event.preventDefault();
    return;
  }
  const CompetitorNameValid = validateCompetitorName();
  if (!CompetitorNameValid) {
    event.preventDefault();
    return;
  }
  const BrandNameValid = validateBrandName();
  if (!BrandNameValid) {
    event.preventDefault();
    return;
  }
  const FounderNameValid = validateFounderName();
  if (!FounderNameValid) {
    event.preventDefault();
    return;
  }
  const CeoNameValid = validateCeoName();
  if (!CeoNameValid) {
    event.preventDefault();
    return;
  }
  const WebsiteValid = validateWebsite();
  if (!WebsiteValid) {
    event.preventDefault();
    return;
  }
  const LinkedinValid = validateLinkedin();
  if (!LinkedinValid) {
    event.preventDefault();
    return;
  }
  const RelevantLinkValid = validateRelevantLink();
  if (!RelevantLinkValid) {
    event.preventDefault();
    return;
  }
});
}

//End of validate startup update submission----------------
//----------startup success message alert------
        setTimeout(function() {
        	//console.log("settimeout"); //check
            var successMessage = document.getElementById("onboardsuccess");
            var CompanyUpdateSuccess = document.getElementById("companyUpdateSuccess");
            var CompanyDeleteSuccess = document.getElementById("companyDeleteSuccess");
            if (successMessage) {
                successMessage.style.display = "none";
            }
            if (CompanyUpdateSuccess) {
                CompanyUpdateSuccess.style.display = "none";
            }
            if (CompanyDeleteSuccess) {
                CompanyDeleteSuccess.style.display = "none";
            }
        }, 2000); // 2000 milliseconds = 2 seconds
//----------end of startup success message alert------



/*
--------------------------------------------------end of customized js-------------------------------------------------------------
 //---------------------------------------------------end of Startup Onboarding & Startup List JS-------------------------------------
 */
//----------institute----------

//--------------------validate Institute name (with numbers)
			var nameInput = document.getElementById("instituteName");
				if (nameInput !== null) {
						//console.log("iname");
					  // Add an event listener to the name input field
					  nameInput.addEventListener("input", validateInstituteName);
					
					  function validateInstituteName() {
						    var name = document.getElementById("instituteName").value;
						    var nameRegex = /^(?=.*[a-zA-Z])[a-zA-Z0-9]+(([',. -][a-zA-Z ])?[a-zA-Z0-9]*)*$/;
						    var instituteNameError = document.getElementById("instituteNameError");
						    
						    if (name.trim() === '') {
						      instituteNameError.style.display = "none"; // Hide error when field is empty
						      return true; // No error when field is empty
						    }
						    
						    if (!nameRegex.test(name)) {
						      instituteNameError.style.display = "block";
						      return false;
						    } else {
						      instituteNameError.style.display = "none";
						      return true;
					    }
				  }
			}
          
            //-----------------------------end of validate institute name       
            //validate Brand name (with numbers)-------
			var nameInput = document.getElementById("brandName");
			if(nameInput !== null){
				// Add an event listener to the name input field
			nameInput.addEventListener("input", validateBrandName);
			
			function validateBrandName() {
				//console.log("validate brandname called");
			  var name = document.getElementById("brandName").value;
			  var nameRegex = /^(?=.*[a-zA-Z])[a-zA-Z0-9]+(([',. -][a-zA-Z ])?[a-zA-Z0-9]*)*$/;
			  var brandNameError = document.getElementById("brandNameError");
			  if (name.trim() === '') {
			      brandNameError.style.display = "none"; // Hide error when field is empty
			      return true; // No error when field is empty
			    }
			  if (!nameRegex.test(name)) {
			    brandNameError.style.display = "block";
			    return false;
			  } else {
				brandNameError.style.display = "none";
			    return true;
			  }
			}
			}
			//end of validate Brand name--------
            //--------------------validate Institute Email 
            var emailInput = document.getElementById("instituteEmail");
            if(emailInput !== null){
            emailInput.addEventListener("input", validateInstituteEmail);
            function validateInstituteEmail() {
             var email = document.getElementById("instituteEmail").value;
             var emailRegex =/^[\w\.-]+@[a-zA-Z\d\.-]+\.[a-zA-Z]{2,}$/;
             var instituteEmailError = document.getElementById("instituteEmailError");
             if (email === "") {
			    instituteEmailError.style.display = "none"; // Hide the error message if the field is empty
			    return true;
			 } else if (!emailRegex.test(email)) {
            	 instituteEmailError.style.display = "block";
               return false;
             } else {
            	 instituteEmailError.style.display = "none";
               return true;
             }
            }
            }//-----------------------------end of validate Institute Email
            

/*Location state and city*/
  var AndraPradesh = ["Anantapur","Chittoor","East Godavari","Guntur","Kadapa","Krishna","Kurnool","Prakasam","Nellore","Srikakulam","Visakhapatnam","Vizianagaram","West Godavari"];
var ArunachalPradesh = ["Anjaw","Changlang","Dibang Valley","East Kameng","East Siang","Kra Daadi","Kurung Kumey","Lohit","Longding","Lower Dibang Valley","Lower Subansiri","Namsai","Papum Pare","Siang","Tawang","Tirap","Upper Siang","Upper Subansiri","West Kameng","West Siang","Itanagar"];
var Assam = ["Baksa","Barpeta","Biswanath","Bongaigaon","Cachar","Charaideo","Chirang","Darrang","Dhemaji","Dhubri","Dibrugarh","Goalpara","Golaghat","Hailakandi","Hojai","Jorhat","Kamrup Metropolitan","Kamrup (Rural)","Karbi Anglong","Karimganj","Kokrajhar","Lakhimpur","Majuli","Morigaon","Nagaon","Nalbari","Dima Hasao","Sivasagar","Sonitpur","South Salmara Mankachar","Tinsukia","Udalguri","West Karbi Anglong"];
var Bihar = ["Araria","Arwal","Aurangabad","Banka","Begusarai","Bhagalpur","Bhojpur","Buxar","Darbhanga","East Champaran","Gaya","Gopalganj","Jamui","Jehanabad","Kaimur","Katihar","Khagaria","Kishanganj","Lakhisarai","Madhepura","Madhubani","Munger","Muzaffarpur","Nalanda","Nawada","Patna","Purnia","Rohtas","Saharsa","Samastipur","Saran","Sheikhpura","Sheohar","Sitamarhi","Siwan","Supaul","Vaishali","West Champaran"];
var Chhattisgarh = ["Balod","Baloda Bazar","Balrampur","Bastar","Bemetara","Bijapur","Bilaspur","Dantewada","Dhamtari","Durg","Gariaband","Janjgir Champa","Jashpur","Kabirdham","Kanker","Kondagaon","Korba","Koriya","Mahasamund","Mungeli","Narayanpur","Raigarh","Raipur","Rajnandgaon","Sukma","Surajpur","Surguja"];
var Goa = ["North Goa","South Goa"];
var Gujarat = ["Ahmedabad","Amreli","Anand","Aravalli","Banaskantha","Bharuch","Bhavnagar","Botad","Chhota Udaipur","Dahod","Dang","Devbhoomi Dwarka","Gandhinagar","Gir Somnath","Jamnagar","Junagadh","Kheda","Kutch","Mahisagar","Mehsana","Morbi","Narmada","Navsari","Panchmahal","Patan","Porbandar","Rajkot","Sabarkantha","Surat","Surendranagar","Tapi","Vadodara","Valsad"];
var Haryana = ["Ambala","Bhiwani","Charkhi Dadri","Faridabad","Fatehabad","Gurugram","Hisar","Jhajjar","Jind","Kaithal","Karnal","Kurukshetra","Mahendragarh","Mewat","Palwal","Panchkula","Panipat","Rewari","Rohtak","Sirsa","Sonipat","Yamunanagar"];
var HimachalPradesh = ["Bilaspur","Chamba","Hamirpur","Kangra","Kinnaur","Kullu","Lahaul Spiti","Mandi","Shimla","Sirmaur","Solan","Una"];
var JammuKashmir = ["Anantnag","Bandipora","Baramulla","Budgam","Doda","Ganderbal","Jammu","Kargil","Kathua","Kishtwar","Kulgam","Kupwara","Leh","Poonch","Pulwama","Rajouri","Ramban","Reasi","Samba","Shopian","Srinagar","Udhampur"];
var Jharkhand = ["Bokaro","Chatra","Deoghar","Dhanbad","Dumka","East Singhbhum","Garhwa","Giridih","Godda","Gumla","Hazaribagh","Jamtara","Khunti","Koderma","Latehar","Lohardaga","Pakur","Palamu","Ramgarh","Ranchi","Sahebganj","Seraikela Kharsawan","Simdega","West Singhbhum"];
var Karnataka = ["Bagalkot","Bangalore Rural","Bangalore Urban","Belgaum","Bellary","Bidar","Vijayapura","Chamarajanagar","Chikkaballapur","Chikkamagaluru","Chitradurga","Dakshina Kannada","Davanagere","Dharwad","Gadag","Gulbarga","Hassan","Haveri","Kodagu","Kolar","Koppal","Mandya","Mysore","Raichur","Ramanagara","Shimoga","Tumkur","Udupi","Uttara Kannada","Yadgir"];
var Kerala = ["Alappuzha","Ernakulam","Idukki","Kannur","Kasaragod","Kollam","Kottayam","Kozhikode","Malappuram","Palakkad","Pathanamthitta","Thiruvananthapuram","Thrissur","Wayanad"];
var MadhyaPradesh = ["Agar Malwa","Alirajpur","Anuppur","Ashoknagar","Balaghat","Barwani","Betul","Bhind","Bhopal","Burhanpur","Chhatarpur","Chhindwara","Damoh","Datia","Dewas","Dhar","Dindori","Guna","Gwalior","Harda","Hoshangabad","Indore","Jabalpur","Jhabua","Katni","Khandwa","Khargone","Mandla","Mandsaur","Morena","Narsinghpur","Neemuch","Panna","Raisen","Rajgarh","Ratlam","Rewa","Sagar","Satna",
"Sehore","Seoni","Shahdol","Shajapur","Sheopur","Shivpuri","Sidhi","Singrauli","Tikamgarh","Ujjain","Umaria","Vidisha"];
var Maharashtra = ["Ahmednagar","Akola","Amravati","Aurangabad","Beed","Bhandara","Buldhana","Chandrapur","Dhule","Gadchiroli","Gondia","Hingoli","Jalgaon","Jalna","Kolhapur","Latur","Mumbai City","Mumbai Suburban","Nagpur","Nanded","Nandurbar","Nashik","Osmanabad","Palghar","Parbhani","Pune","Raigad","Ratnagiri","Sangli","Satara","Sindhudurg","Solapur","Thane","Wardha","Washim","Yavatmal"];
var Manipur = ["Bishnupur","Chandel","Churachandpur","Imphal East","Imphal West","Jiribam","Kakching","Kamjong","Kangpokpi","Noney","Pherzawl","Senapati","Tamenglong","Tengnoupal","Thoubal","Ukhrul"];
var Meghalaya = ["East Garo Hills","East Jaintia Hills","East Khasi Hills","North Garo Hills","Ri Bhoi","South Garo Hills","South West Garo Hills","South West Khasi Hills","West Garo Hills","West Jaintia Hills","West Khasi Hills"];
var Mizoram = ["Aizawl","Champhai","Kolasib","Lawngtlai","Lunglei","Mamit","Saiha","Serchhip","Aizawl","Champhai","Kolasib","Lawngtlai","Lunglei","Mamit","Saiha","Serchhip"];
var Nagaland = ["Dimapur","Kiphire","Kohima","Longleng","Mokokchung","Mon","Peren","Phek","Tuensang","Wokha","Zunheboto"];
var Odisha = ["Angul","Balangir","Balasore","Bargarh","Bhadrak","Boudh","Cuttack","Debagarh","Dhenkanal","Gajapati","Ganjam","Jagatsinghpur","Jajpur","Jharsuguda","Kalahandi","Kandhamal","Kendrapara","Kendujhar","Khordha","Koraput","Malkangiri","Mayurbhanj","Nabarangpur","Nayagarh","Nuapada","Puri","Rayagada","Sambalpur","Subarnapur","Sundergarh"];
var Punjab = ["Amritsar","Barnala","Bathinda","Faridkot","Fatehgarh Sahib","Fazilka","Firozpur","Gurdaspur","Hoshiarpur","Jalandhar","Kapurthala","Ludhiana","Mansa","Moga","Mohali","Muktsar","Pathankot","Patiala","Rupnagar","Sangrur","Shaheed Bhagat Singh Nagar","Tarn Taran"];
var Rajasthan = ["Ajmer","Alwar","Banswara","Baran","Barmer","Bharatpur","Bhilwara","Bikaner","Bundi","Chittorgarh","Churu","Dausa","Dholpur","Dungarpur","Ganganagar","Hanumangarh","Jaipur","Jaisalmer","Jalore","Jhalawar","Jhunjhunu","Jodhpur","Karauli","Kota","Nagaur","Pali","Pratapgarh","Rajsamand","Sawai Madhopur","Sikar","Sirohi","Tonk","Udaipur"];
var Sikkim = ["East Sikkim","North Sikkim","South Sikkim","West Sikkim"];
var TamilNadu = ["Ariyalur","Chennai","Coimbatore","Cuddalore","Dharmapuri","Dindigul","Erode","Kanchipuram","Kanyakumari","Karur","Krishnagiri","Madurai","Nagapattinam","Namakkal","Nilgiris","Perambalur","Pudukkottai","Ramanathapuram","Salem","Sivaganga","Thanjavur","Theni","Thoothukudi","Tiruchirappalli","Tirunelveli","Tiruppur","Tiruvallur","Tiruvannamalai","Tiruvarur","Vellore","Viluppuram","Virudhunagar"];
var Telangana = ["Adilabad","Bhadradri Kothagudem","Hyderabad","Jagtial","Jangaon","Jayashankar","Jogulamba","Kamareddy","Karimnagar","Khammam","Komaram Bheem","Mahabubabad","Mahbubnagar","Mancherial","Medak","Medchal","Nagarkurnool","Nalgonda","Nirmal","Nizamabad","Peddapalli","Rajanna Sircilla","Ranga Reddy","Sangareddy","Siddipet","Suryapet","Vikarabad","Wanaparthy","Warangal Rural","Warangal Urban","Yadadri Bhuvanagiri"];
var Tripura = ["Dhalai","Gomati","Khowai","North Tripura","Sepahijala","South Tripura","Unakoti","West Tripura"];
var UttarPradesh = ["Agra","Aligarh","Allahabad","Ambedkar Nagar","Amethi","Amroha","Auraiya","Azamgarh","Baghpat","Bahraich","Ballia","Balrampur","Banda","Barabanki","Bareilly","Basti","Bhadohi","Bijnor","Budaun","Bulandshahr","Chandauli","Chitrakoot","Deoria","Etah","Etawah","Faizabad","Farrukhabad","Fatehpur","Firozabad","Gautam Buddha Nagar","Ghaziabad","Ghazipur","Gonda","Gorakhpur","Hamirpur","Hapur","Hardoi","Hathras","Jalaun","Jaunpur","Jhansi","Kannauj","Kanpur Dehat","Kanpur Nagar","Kasganj","Kaushambi","Kheri","Kushinagar","Lalitpur","Lucknow","Maharajganj","Mahoba","Mainpuri","Mathura","Mau","Meerut","Mirzapur","Moradabad","Muzaffarnagar","Pilibhit","Pratapgarh","Raebareli","Rampur","Saharanpur","Sambhal","Sant Kabir Nagar","Shahjahanpur","Shamli","Shravasti","Siddharthnagar","Sitapur","Sonbhadra","Sultanpur","Unnao","Varanasi"];
var Uttarakhand  = ["Almora","Bageshwar","Chamoli","Champawat","Dehradun","Haridwar","Nainital","Pauri","Pithoragarh","Rudraprayag","Tehri","Udham Singh Nagar","Uttarkashi"];
var WestBengal = ["Alipurduar","Bankura","Birbhum","Cooch Behar","Dakshin Dinajpur","Darjeeling","Hooghly","Howrah","Jalpaiguri","Jhargram","Kalimpong","Kolkata","Malda","Murshidabad","Nadia","North 24 Parganas","Paschim Bardhaman","Paschim Medinipur","Purba Bardhaman","Purba Medinipur","Purulia","South 24 Parganas","Uttar Dinajpur"];
var AndamanNicobar = ["Nicobar","North Middle Andaman","South Andaman"];
var Chandigarh = ["Chandigarh"];
var DadraHaveli = ["Dadra Nagar Haveli"];
var DamanDiu = ["Daman","Diu"];
var Delhi = ["Central Delhi","East Delhi","New Delhi","North Delhi","North East Delhi","North West Delhi","Shahdara","South Delhi","South East Delhi","South West Delhi","West Delhi"];
var Lakshadweep = ["Lakshadweep"];
var Puducherry = ["Karaikal","Mahe","Puducherry","Yanam"];


$("#inputState").change(function(){
  var StateSelected = $(this).val();
  var optionsList;
  var htmlString = "";

  switch (StateSelected) {
    case "Andra Pradesh":
        optionsList = AndraPradesh;
        break;
    case "Arunachal Pradesh":
        optionsList = ArunachalPradesh;
        break;
    case "Assam":
        optionsList = Assam;
        break;
    case "Bihar":
        optionsList = Bihar;
        break;
    case "Chhattisgarh":
        optionsList = Chhattisgarh;
        break;
    case "Goa":
        optionsList = Goa;
        break;
    case  "Gujarat":
        optionsList = Gujarat;
        break;
    case "Haryana":
        optionsList = Haryana;
        break;
    case "Himachal Pradesh":
        optionsList = HimachalPradesh;
        break;
    case "Jammu and Kashmir":
        optionsList = JammuKashmir;
        break;
    case "Jharkhand":
        optionsList = Jharkhand;
        break;
    case  "Karnataka":
        optionsList = Karnataka;
        break;
    case "Kerala":
        optionsList = Kerala;
        break;
    case  "Madya Pradesh":
        optionsList = MadhyaPradesh;
        break;
    case "Maharashtra":
        optionsList = Maharashtra;
        break;
    case  "Manipur":
        optionsList = Manipur;
        break;
    case "Meghalaya":
        optionsList = Meghalaya ;
        break;
    case  "Mizoram":
        optionsList = Mizoram;
        break;
    case "Nagaland":
        optionsList = Nagaland;
        break;
    case  "Orissa":
        optionsList = Orissa;
        break;
    case "Punjab":
        optionsList = Punjab;
        break;
    case  "Rajasthan":
        optionsList = Rajasthan;
        break;
    case "Sikkim":
        optionsList = Sikkim;
        break;
    case  "Tamil Nadu":
        optionsList = TamilNadu;
        break;
    case  "Telangana":
        optionsList = Telangana;
        break;
    case "Tripura":
        optionsList = Tripura ;
        break;
    case  "Uttaranchal":
        optionsList = Uttaranchal;
        break;
    case  "Uttar Pradesh":
        optionsList = UttarPradesh;
        break;
    case "West Bengal":
        optionsList = WestBengal;
        break;
    case  "Andaman and Nicobar Islands":
        optionsList = AndamanNicobar;
        break;
    case "Chandigarh":
        optionsList = Chandigarh;
        break;
    case  "Dadar and Nagar Haveli":
        optionsList = DadraHaveli;
        break;
    case "Daman and Diu":
        optionsList = DamanDiu;
        break;
    case  "Delhi":
        optionsList = Delhi;
        break;
    case "Lakshadeep":
        optionsList = Lakshadeep ;
        break;
    case  "Pondicherry":
        optionsList = Pondicherry;
        break;
}


  for(var i = 0; i < optionsList.length; i++){
    htmlString = htmlString+"<option value='"+ optionsList[i] +"'>"+ optionsList[i] +"</option>";
  }
  $("#inputDistrict").html(htmlString);

});
const selectBtn = document.querySelector(".select-btn"),
      items = document.querySelectorAll(".item");
if(selectBtn !== null){
	selectBtn.addEventListener("click", () => {
    selectBtn.classList.toggle("open");
});
}

items.forEach(item => {
    item.addEventListener("click", () => {
        item.classList.toggle("checked");

        let checked = document.querySelectorAll(".checked"),
            btnText = document.querySelector(".btn-text");

            if(checked && checked.length > 0){
                btnText.innerText = `${checked.length} Selected`;
            }else{
                btnText.innerText = "Select Language";
            }
    });
})

//-------------------------------User Email Validation -----------------

if(emailInput !== null){
	// Add an event listener to the email input field
emailInput.addEventListener("input", validateUserEmail);

function validateUserEmail() {
  var email = document.getElementById("email").value;
  var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Basic email regex
  var userEmailError = document.getElementById("emailError");
  if (!emailRegex.test(email)) {
    userEmailError.style.display = "block";
    return false;
  } else {
    userEmailError.style.display = "none";
    return true;
  }
}
}//github.com/rugwedpatharkar/CareerForAll2.git
//------------------End Of User Email Validation----------------



// ---------------------Admin side CompanyNames Dropdown validation ----------------
function toggleDropdown(selectedRole) {
        var dynamicDropdown = document.getElementById("dynamicDropdown");
        
        if (selectedRole === "HR") {
            dynamicDropdown.style.display = "block";
        } else if (selectedRole === "PO") {
            dynamicDropdown.style.display = "block";
        } else {
            dynamicDropdown.style.display = "none";
        }
    }
//----------------------------------------------------------------------------------------

