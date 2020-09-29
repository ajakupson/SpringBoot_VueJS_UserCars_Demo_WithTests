import MainLayout from '../layouts/Main'
import VLink from '../components/VLink'

export default {
	template: `<main-layout>
				<h1>This Is Application Home Page</h1>
				<p><v-link href="/users">Users</v-link></p>
				<p><v-link href="/cars">Cars</v-link></p>
			   </main-layout>`,
	components: {
		'v-link': VLink,
		'main-layout': MainLayout
    }
}