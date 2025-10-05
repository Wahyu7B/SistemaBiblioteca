// Prefill del título en el modal
const prestamoModal = document.getElementById('modalPrestamo');
if (prestamoModal) {
  prestamoModal.addEventListener('show.bs.modal', (ev) => {
    const btn = ev.relatedTarget;
    const libro = btn?.getAttribute('data-libro') || '';
    const input = document.getElementById('libroSeleccionado');
    if (input) input.value = libro;

    // fecha mínima: mañana
    const f = document.getElementById('fechaDevolucion');
    if (f) {
      const today = new Date();
      today.setDate(today.getDate() + 1);
      const min = today.toISOString().split('T')[0];
      f.min = min;
      if (!f.value) f.value = min;
    }
  });
}

// Validación + envío ficticio con Toast
const form = document.getElementById('formPrestamo');
if (form) {
  form.addEventListener('submit', (e) => {
    if (!form.checkValidity()) {
      e.preventDefault();
      e.stopPropagation();
    } else {
      e.preventDefault();

      // Cierra modal
      const modal = bootstrap.Modal.getInstance(prestamoModal);
      modal?.hide();

      // Limpia y feedback
      form.reset();
      form.classList.remove('was-validated');

      // Toast bonito
      const toastEl = document.getElementById('toastOk');
      if (toastEl) new bootstrap.Toast(toastEl).show();
    }
    form.classList.add('was-validated');
  }, false);
}

// ScrollSpy (para resaltar item activo en navbar)
document.addEventListener('shown.bs.collapse', () => bootstrap.ScrollSpy.getInstance(document.body)?.refresh());
document.addEventListener('DOMContentLoaded', () => {
  bootstrap.ScrollSpy.getOrCreateInstance(document.body, { target: '#mainNav', rootMargin: '0px 0px -40%' });
});

// Suavizar anclas en iOS/desktop (ya usamos smooth-scroll en body, esto es por compatibilidad)
document.querySelectorAll('a[href^="#"]').forEach(a=>{
  a.addEventListener('click', (e)=>{
    const id = a.getAttribute('href');
    if (!id || id === '#') return;
    const el = document.querySelector(id);
    if (!el) return;
    e.preventDefault();
    el.scrollIntoView({ behavior:'smooth', block:'start' });
  });
});

// Botón "volver arriba"
const btnTop = document.getElementById('btnTop');
const onScroll = () => {
  if (!btnTop) return;
  const y = window.scrollY || document.documentElement.scrollTop;
  btnTop.classList.toggle('show', y > 600);
};
window.addEventListener('scroll', onScroll);
btnTop?.addEventListener('click', ()=> window.scrollTo({ top:0, behavior:'smooth' }));

// Validación de celular: solo números
const cel = document.getElementById('celular');
cel?.addEventListener('input', () => { cel.value = cel.value.replace(/\D/g,'').slice(0,9); });
