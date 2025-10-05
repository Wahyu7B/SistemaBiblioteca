document.addEventListener('DOMContentLoaded', () => {
  const q = document.getElementById('searchPrestamos');
  const rows = document.querySelectorAll('#tbPrestamos tr');
  if (!q) return;
  q.addEventListener('input', () => {
    const val = q.value.toLowerCase().trim();
    rows.forEach(tr => tr.style.display = tr.innerText.toLowerCase().includes(val) ? '' : 'none');
  });
});
