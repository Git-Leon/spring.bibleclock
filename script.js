$(document).ready(function() {
    $.ajax({
        url: 'data.csv',
        dataType: 'text',
        success: function(data) {
            // Parse CSV data
            const rows = data.split('\n');
            const headers = rows[0].split(',');
            
            // Create table
            let table = '<table>';
            
            // Add header row
            table += '<thead><tr>';
            headers.forEach(header => {
                table += `<th>${header.trim()}</th>`;
            });
            table += '</tr></thead>';
            
            // Add data rows
            table += '<tbody>';
            for (let i = 1; i < rows.length; i++) {
                if (rows[i].trim() === '') continue;
                
                const cells = rows[i].split(',');
                table += '<tr>';
                cells.forEach(cell => {
                    table += `<td>${cell.trim()}</td>`;
                });
                table += '</tr>';
            }
            table += '</tbody></table>';
            
            // Insert table into the page
            $('#csv-output').html(table);
        },
        error: function(error) {
            $('#csv-output').html('<p>Error loading the CSV file. Please try again later.</p>');
            console.error('Error loading CSV:', error);
        }
    });
}); 