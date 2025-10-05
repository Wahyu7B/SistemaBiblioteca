

$(document).ready(function() {
    // Variables globales
    let dataTable;
    let isEditing = false;
    let usuarioModal;

    // Configuración inicial
    const API_BASE = '/usuarios/api';
    const ENDPOINTS = {
        list: `${API_BASE}/listar`,
        save: `${API_BASE}/guardar`,
        get: (id) => `${API_BASE}/${id}`,
        delete: (id) => `${API_BASE}/eliminar/${id}`,
        profiles: `${API_BASE}/perfiles`,
        toggleStatus: (id) => `${API_BASE}/cambiar-estado/${id}`,
    };

    initializeDataTable(); 

    usuarioModal = new bootstrap.Modal(document.getElementById('usuarioModal'));

    loadProfiles();

    setupEventListeners();

    function initializeDataTable() {
        dataTable = $('#tablaUsuarios').DataTable({
            responsive: true,
            processing: true,
            ajax: {
                url: ENDPOINTS.list,
                dataSrc: 'data' 
            },
            columns: [
                { data: 'id' },
                { data: 'nombre' },
                { data: 'usuario' },
                { data: 'perfil.nombre' }, 
                { data: 'correo' },
                {
                    data: 'estado',
                    render: function(data, type, row) {
                        return data === 1
                            ? '<span class="badge text-bg-success">Activo</span>' 
                            : '<span class="badge text-bg-danger">Inactivo</span>';
                    }
                },
                {
                    data: null,
                    orderable: false,
                    searchable: false,
                    render: function(data, type, row) {
                        return createActionButtons(row);
                    }
                }
            ],
            columnDefs: [
                { responsivePriority: 1, targets: 1 }, 
                { responsivePriority: 2, targets: 6 }, 
            ],
            language: {
                url: "//cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json",
            },
            pageLength: 10
        });
    }


    function createActionButtons(row) {
        const statusIcon = row.estado === 1
            ? '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye-slash-fill" viewBox="0 0 16 16"><path d="m10.79 12.912-1.614-1.615a3.5 3.5 0 0 1-4.474-4.474l-2.06-2.06C.938 6.278 0 8 0 8s3 5.5 8 5.5a7.029 7.029 0 0 0 2.79-.588M5.21 3.088A7.028 7.028 0 0 1 8 2.5c5 0 8 5.5 8 5.5s-.939 1.721-2.641 3.238l-2.062-2.062a3.5 3.5 0 0 0-4.474-4.474L5.21 3.089z"/><path d="M5.525 7.646a2.5 2.5 0 0 0 2.829 2.829l-2.83-2.829zm4.95.708-2.829-2.83a2.5 2.5 0 0 1 2.829 2.829zm3.171 6-12-12 .708-.708 12 12z"/></svg>'
            : '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye-fill" viewBox="0 0 16 16"><path d="M10.5 8a2.5 2.5 0 1 1-5 0 2.5 2.5 0 0 1 5 0"/><path d="M0 8s3-5.5 8-5.5S16 8 16 8s-3 5.5-8 5.5S0 8 0 8m8 3.5a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7"/></svg>';

        const statusClass = row.estado === 1 ? 'action-btn-status-deactivate' : 'action-btn-status-activate';
        const statusTitle = row.estado === 1 ? 'Desactivar' : 'Activar';

        return `
            <div class="d-flex gap-1">
                <button data-id="${row.id}" class="action-btn action-btn-edit action-edit" title="Editar">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16"><path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/><path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z"/></svg>
                </button>
                <button data-id="${row.id}" class="action-btn action-status ${statusClass}" title="${statusTitle}">
                    ${statusIcon}
                </button>
                <button data-id="${row.id}" class="action-btn action-btn-delete action-delete" title="Eliminar">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3-fill" viewBox="0 0 16 16"><path d="M11 1.5v1h3.5a.5.5 0 0 1 0 1h-.538l-.853 10.66A2 2 0 0 1 11.115 16h-6.23a2 2 0 0 1-1.994-1.84L2.038 3.5H1.5a.5.5 0 0 1 0-1H5v-1A1.5 1.5 0 0 1 6.5 0h3A1.5 1.5 0 0 1 11 1.5m-5 0v1h4v-1a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5M4.5 5.029l.5 8.5a.5.5 0 1 0 .998-.06l-.5-8.5a.5.5 0 1 0-.998.06m6.53-.528a.5.5 0 0 0-.528.47l-.5 8.5a.5.5 0 0 0 .998.058l.5-8.5a.5.5 0 0 0-.47-.528M8 4.5a.5.5 0 0 0-.5.5v8.5a.5.5 0 0 0 1 0V5a.5.5 0 0 0-.5-.5"/></svg>
                </button>
            </div>
        `;
    }


    function setupEventListeners() {

        $('#btnNuevoRegistro').on('click', openModalForNew);

        $('#formUsuario').on('submit', function(e) {
            e.preventDefault();
            saveUsuario();
        });


        $('#tablaUsuarios tbody').on('click', '.action-edit', handleEdit);
        $('#tablaUsuarios tbody').on('click', '.action-status', handleToggleStatus);
        $('#tablaUsuarios tbody').on('click', '.action-delete', handleDelete);
    }

    function loadUsuarios() {
        dataTable.ajax.reload();
    }

    function loadProfiles() {
        fetch(ENDPOINTS.profiles)
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    const select = $('#id_perfil');
                    select.empty().append('<option value="">Seleccione un perfil...</option>');
                    data.data.forEach(profile => {
                        select.append(`<option value="${profile.id}">${profile.nombre}</option>`);
                    });
                } else {
                    showNotification('Error al cargar perfiles', 'error');
                }
            }).catch(error => {
                console.error('Error cargando perfiles:', error);
            });
    }


    function saveUsuario() {
        clearFieldErrors();

        const formData = {
            id: $('#id').val() || null,
            nombre: $('#nombre').val().trim(),
            usuario: $('#usuario').val().trim(),
            clave: $('#clave').val(),
            correo: $('#correo').val().trim(),
            perfil: {
                id: $('#id_perfil').val()
            }
        };

        if (!validateForm(formData)) {
            return;
        }

        if (isEditing && !formData.clave) {
            delete formData.clave;
        }

        showLoading(true);

        fetch(ENDPOINTS.save, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                hideModal();
                showNotification(data.message, 'success');
                loadUsuarios();
            } else {
                if (data.errors) {
                    Object.keys(data.errors).forEach(field => {
                        showFieldError(field, data.errors[field]);
                    });
                } else {
                    showNotification(data.message, 'error');
                }
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showNotification('Error de conexión al guardar usuario', 'error');
        })
        .finally(() => {
            showLoading(false);
        });
    }

    function handleEdit(e) {
        e.preventDefault();
        const id = $(this).data('id');

        showLoading(true);

        fetch(ENDPOINTS.get(id))
            .then(response => {
                if (!response.ok) {
                    throw new Error('Usuario no encontrado');
                }
                return response.json();
            })
            .then(data => {
                if (data.success) {
                    openModalForEdit(data.data);
                } else {
                    showNotification('Error al cargar usuario: ' + data.message, 'error');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                showNotification('Error al cargar los datos del usuario', 'error');
            })
            .finally(() => {
                showLoading(false);
            });
    }


    function handleToggleStatus(e) {
        e.preventDefault();
        const id = $(this).data('id');

        showLoading(true);

        fetch(ENDPOINTS.toggleStatus(id), {
            method: 'POST'
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                showNotification(data.message, 'success');
                loadUsuarios(); 
            } else {
                showNotification('Error: ' + data.message, 'error');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showNotification('Error de conexión al cambiar estado', 'error');
        })
        .finally(() => {
            showLoading(false);
        });
    }

    function handleDelete(e) {
        e.preventDefault();

        const id = $(this).data('id');

        Swal.fire({
            title: '¿Estás seguro?',
            text: "¡No podrás revertir esta acción!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#dc3545',
            cancelButtonColor: '#6c757d',
            confirmButtonText: 'Sí, ¡eliminar!',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                showLoading(true);

                fetch(ENDPOINTS.delete(id), {
                    method: 'DELETE'
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        showNotification(data.message, 'success');
                        loadUsuarios(); // Recargar la tabla
                    } else {
                        showNotification('Error: ' + data.message, 'error');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    showNotification('Error de conexión al eliminar usuario', 'error');
                })
                .finally(() => {
                    showLoading(false);
                });
            }
        });
    }

    /**
     * Abre el modal para crear nuevo usuario
     */
    function openModalForNew() {
        isEditing = false;
        clearForm();
        $('#modalTitle').text('Agregar Usuario');
        $('#clave').prop('required', true).attr('placeholder', '');
        showModal();
    }

    /**
     * Abre el modal para editar usuario
     */
    function openModalForEdit(usuario) {
        isEditing = true;
        clearForm();
        $('#modalTitle').text('Editar Usuario');

        $('#id').val(usuario.id);
        $('#nombre').val(usuario.nombre);
        $('#usuario').val(usuario.usuario);
        $('#correo').val(usuario.correo);
        $('#id_perfil').val(usuario.perfil ? usuario.perfil.id : '');
        $('#clave').val('').prop('required', false).attr('placeholder', 'Dejar en blanco para no cambiar');

        showModal();
    }

    /**
     * Muestra el modal
     */
    function showModal() {
        usuarioModal.show();
    }

    /**
     * Oculta el modal
     */
    function hideModal() {
        usuarioModal.hide();
        clearForm();
    }

    /**
     * Limpia el formulario y resetea el estado
     */
    function clearForm() {
        $('#formUsuario')[0].reset();
        $('#formUsuario .form-control').removeClass('is-invalid');
        $('.invalid-feedback').text('');
        isEditing = false;
    }

    /**
     * Valida el formulario del lado cliente
     */
    function validateForm(formData) {
        let hasErrors = false;
        clearFieldErrors();

        if (!formData.nombre) {
            showFieldError('nombre', 'El nombre es obligatorio');
            hasErrors = true;
        } else if (formData.nombre.length < 2) {
            showFieldError('nombre', 'El nombre debe tener al menos 2 caracteres');
            hasErrors = true;
        }

        if (!formData.usuario) {
            showFieldError('usuario', 'El usuario es obligatorio');
            hasErrors = true;
        } else if (formData.usuario.length < 3) {
            showFieldError('usuario', 'El usuario debe tener al menos 3 caracteres');
            hasErrors = true;
        }

        if (!formData.perfil.id) {
            showFieldError('id_perfil', 'Debe seleccionar un perfil');
            hasErrors = true;
        }

        if (!isEditing && !formData.clave) {
            showFieldError('clave', 'La contraseña es obligatoria');
            hasErrors = true;
        } else if (formData.clave && formData.clave.length < 6) {
            showFieldError('clave', 'La contraseña debe tener al menos 6 caracteres');
            hasErrors = true;
        }

        if (!formData.correo) {
            showFieldError('correo', 'El correo es obligatorio');
            hasErrors = true;
        } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.correo)) {
            showFieldError('correo', 'El formato del correo no es válido');
            hasErrors = true;
        }

        return !hasErrors;
    }

    /**
     * Muestra error en un campo específico
     */
    function showFieldError(fieldName, message) {
        const field = $(`#${fieldName}`);
        const errorDiv = $(`#${fieldName}-error`);

        field.addClass('is-invalid');
        errorDiv.text(message);
    }

    /**
     * Limpia todos los errores de campo
     */
    function clearFieldErrors() {
        $('.invalid-feedback').text('');
        $('#formUsuario .form-control').removeClass('is-invalid');
    }

    /**
     * Muestra notificaciones toast
     */
    function showNotification(message, type = 'success') {
        const toastClass = type === 'success' ? 'text-bg-success' : 'text-bg-danger';

        const notification = $(`
            <div class="toast align-items-center ${toastClass} border-0" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="d-flex">
                    <div class="toast-body">
                        ${message}
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
            </div>
        `);

        $('#notification-container').append(notification);
        
        const toast = new bootstrap.Toast(notification, {
            delay: 5000
        });
        toast.show();
    }

    /**
     * Muestra/oculta indicador de carga
     */
    function showLoading(show) {
        const overlayId = 'loading-overlay';
        const $overlay = $(`#${overlayId}`);

        if (show) {
            if ($overlay.length === 0) {
                const spinner = $('<div>', { class: 'spinner-border text-primary', role: 'status' })
                    .append($('<span>', { class: 'visually-hidden' }).text('Loading...'));
                const newOverlay = $('<div>', { id: overlayId, class: 'loading-overlay' })
                    .append(spinner);
                $('body').append(newOverlay);
            }
        } else {
            $overlay.remove();
        }
    }
});